package alararestaurant.domain.entities;

import alararestaurant.errors.ErrorConstants;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    @NotNull(message = ErrorConstants.INVALID_DATA)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @NotNull(message = ErrorConstants.INVALID_DATA)
    private Item item;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = ErrorConstants.INVALID_DATA)
    private Integer quantity;

    public OrderItem() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
