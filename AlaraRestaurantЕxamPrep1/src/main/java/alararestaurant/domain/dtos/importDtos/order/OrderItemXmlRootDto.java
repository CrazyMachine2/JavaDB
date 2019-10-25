package alararestaurant.domain.dtos.importDtos.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderItemXmlRootDto {

    @XmlElement(name = "item")
    List<OrderItemXmlDto> orderItems;

    public OrderItemXmlRootDto() {
    }

    public List<OrderItemXmlDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemXmlDto> orderItems) {
        this.orderItems = orderItems;
    }
}
