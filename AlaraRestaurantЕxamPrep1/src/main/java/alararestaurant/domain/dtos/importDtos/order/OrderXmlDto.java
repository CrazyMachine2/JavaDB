package alararestaurant.domain.dtos.importDtos.order;

import alararestaurant.domain.entities.OrderType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderXmlDto {

    @XmlElement
    String customer;
    @XmlElement
    String employee;
    @XmlElement(name = "date-time")
    String dateTime;
    @XmlElement
    OrderType type;
    @XmlElement
    OrderItemXmlRootDto items;

    public OrderXmlDto() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderItemXmlRootDto getItems() {
        return items;
    }

    public void setItems(OrderItemXmlRootDto items) {
        this.items = items;
    }
}
