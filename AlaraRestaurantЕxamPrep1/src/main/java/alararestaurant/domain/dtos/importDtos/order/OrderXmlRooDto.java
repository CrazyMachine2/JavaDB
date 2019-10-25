package alararestaurant.domain.dtos.importDtos.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderXmlRooDto {

    @XmlElement(name = "order")
    private List<OrderXmlDto> orders;

    public OrderXmlRooDto() {
    }

    public List<OrderXmlDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderXmlDto> orders) {
        this.orders = orders;
    }
}
