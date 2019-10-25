package alararestaurant.service;

import alararestaurant.domain.dtos.importDtos.order.OrderItemXmlDto;
import alararestaurant.domain.dtos.importDtos.order.OrderXmlDto;
import alararestaurant.domain.dtos.importDtos.order.OrderXmlRooDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.errors.ErrorConstants;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import alararestaurant.util.constants.ImportConstants;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    public OrderServiceImpl(OrderRepository orderRepository, EmployeeRepository employeeRepository, ItemRepository itemRepository, ModelMapper modelMapper, XmlParser xmlParser, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(ImportConstants.ORDERS_XML);
    }

    @Override
    public String importOrders() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        OrderXmlRooDto orderRootDto = this.xmlParser.importXml(ImportConstants.ORDERS_XML,OrderXmlRooDto.class);

        boolean itemExists = true;

        for (OrderXmlDto orderDto : orderRootDto.getOrders()) {
            Order order = this.modelMapper.map(orderDto,Order.class);
            Employee employee = this.employeeRepository.findEmployeeByName(orderDto.getEmployee()).orElse(null);

            if(employee == null){
                sb.append(ErrorConstants.INVALID_DATA).append(System.lineSeparator());
            }

            List<OrderItem> orderItems = new ArrayList<>();

            for (OrderItemXmlDto orderItemDto : orderDto.getItems().getOrderItems()) {
                Item item  = this.itemRepository.findItemByName(orderItemDto.getName()).orElse(null);

                if(item == null){
                    sb.append(ErrorConstants.INVALID_DATA).append(System.lineSeparator());
                    itemExists = false;
                    break;
                }

                OrderItem orderItem = this.modelMapper.map(orderItemDto,OrderItem.class);
                orderItem.setItem(item);
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            }

            if(!itemExists){
                continue;
            }

            LocalDateTime dateTime = LocalDateTime.parse(orderDto.getDateTime(), DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm", Locale.ENGLISH));
            order.setDateTime(dateTime);
            order.setEmployee(employee);
            order.setOrderItems(orderItems);

            if(!this.validationUtil.isValid(order)){
                String message = new ArrayList<>(this.validationUtil.violations(order)).get(0).getMessage();
                sb.append(message).append(System.lineSeparator());
                continue;
            }

            this.orderRepository.saveAndFlush(order);
            sb.append(String.format("Order for %s on %s added.",order.getCustomer(),order.getDateTime().toString())).append(System.lineSeparator());
        }    
        return sb.toString().trim();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        StringBuilder sb = new StringBuilder();

        List<Order> orders = this.orderRepository.findAllByEmployeePositionNameOrderByEmployeeNameAscIdAsc("Burger Flipper");

        for (Order order : orders) {
            sb.append(String.format("Name: %s", order.getEmployee().getName())).append(System.lineSeparator());
            sb.append("Orders:").append(System.lineSeparator());
            sb.append(String.format("   Customer: %s", order.getCustomer())).append(System.lineSeparator());
            sb.append("   Items:").append(System.lineSeparator());

            for (OrderItem orderItem : order.getOrderItems()) {
                sb.append(String.format("     Name: %s", orderItem.getItem().getName())).append(System.lineSeparator());
                sb.append(String.format("     Price: %s", orderItem.getItem().getPrice())).append(System.lineSeparator());
                sb.append(String.format("     Quantity: %d", orderItem.getQuantity())).append(System.lineSeparator());
                sb.append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
