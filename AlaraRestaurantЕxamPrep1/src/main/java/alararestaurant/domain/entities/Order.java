package alararestaurant.domain.entities;

import alararestaurant.errors.ErrorConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "customer", nullable = false, length = 1000)
    private String customer;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderType type = OrderType.ForHere;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @NotNull(message = ErrorConstants.INVALID_DATA)
    private Employee employee;

    @OneToMany(mappedBy = "order", targetEntity = OrderItem.class, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
