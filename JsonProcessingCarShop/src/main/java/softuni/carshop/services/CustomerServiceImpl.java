package softuni.carshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.jsondtos.CustomerOrderedDto;
import softuni.carshop.domain.dtos.jsondtos.CustomerSeedDto;
import softuni.carshop.domain.dtos.jsondtos.CustomerTotalSalesDto;
import softuni.carshop.domain.dtos.xmldtos.ordercustomers.CustomerOrderExportDto;
import softuni.carshop.domain.dtos.xmldtos.ordercustomers.CustomerRootOrderExportDto;
import softuni.carshop.domain.dtos.xmldtos.seed.CustomerDto;
import softuni.carshop.domain.dtos.xmldtos.seed.CustomerRootDto;
import softuni.carshop.domain.entities.Customer;
import softuni.carshop.domain.entities.Part;
import softuni.carshop.domain.entities.Sale;
import softuni.carshop.repositories.CustomerRepository;
import softuni.carshop.services.base.CustomerService;
import softuni.carshop.utils.constants.PathConstantsExport;
import softuni.carshop.utils.constants.PathConstantsImport;
import softuni.carshop.utils.xmlparser.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    /*Json*/
    @Override
    @Transactional
    public List<CustomerTotalSalesDto> getCustomerTotalSales() {
        List<Customer> sales = this.customerRepository.getAllSalesFromCustomer();

        List<CustomerTotalSalesDto> salesDtos = sales.stream()
                .map(s -> this.modelMapper.map(s, CustomerTotalSalesDto.class))
                .collect(Collectors.toList());

        int currentIndex = 0;
        for (CustomerTotalSalesDto salesDto : salesDtos) {
            Customer customer = sales.get(currentIndex);
            currentIndex++;

            BigDecimal totalSpent = new BigDecimal(0);

            for (Sale s : customer.getSales()) {
                totalSpent = totalSpent.add(s.getCar().getParts().stream()
                        .map(Part::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
            }

            salesDto.setBoughtCars(customer.getSales().size());
            salesDto.setSpentMoney(totalSpent);
        }

        return salesDtos.stream().sorted((s1, s2) -> {
            if (s1.getSpentMoney().compareTo(s2.getSpentMoney()) == 0) {
                return s2.getBoughtCars().compareTo(s1.getBoughtCars());
            }
            return s2.getSpentMoney().compareTo(s1.getSpentMoney());
        }).collect(Collectors.toList());

    }

    @Override
    public List<CustomerOrderedDto> orderedCustomers() {

        return this.customerRepository.findOrderedCustomers()
                .stream()
                .map(c -> this.modelMapper.map(c,CustomerOrderedDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void seedCustomers(CustomerSeedDto[] customerSeedDtos) {
        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
            Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);
            this.customerRepository.saveAndFlush(customer);
        }
    }

/*XML BELOW*/

    @Override
    public void orderedCustomersXml() throws JAXBException {

        List<CustomerOrderExportDto> customerDtos = this.customerRepository.findOrderedCustomers()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderExportDto.class))
                .collect(Collectors.toList());

        CustomerRootOrderExportDto rootCustomer = new CustomerRootOrderExportDto();
        rootCustomer.setCustomers(customerDtos);

        this.xmlParser.exportToXml(rootCustomer,CustomerRootOrderExportDto.class, PathConstantsExport.ORDERED_CUSTOMERS_XML);
    }

    @Override
    public void seedCustomersXml() throws JAXBException, ParseException {
        JAXBContext context = JAXBContext.newInstance(CustomerRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CustomerRootDto list = (CustomerRootDto) unmarshaller.unmarshal(new File(PathConstantsImport.CUSTOMERS_XML_PATH));

        for (CustomerDto dto : list.getCustomers()) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            Date date = format.parse(dto.getBirthDate());
            dto.setBirthDate(null);

            Customer customer = this.modelMapper.map(dto,Customer.class);
            customer.setBirthDate(date);

            this.customerRepository.saveAndFlush(customer);
        }



    }
}
