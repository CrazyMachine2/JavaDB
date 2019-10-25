package alararestaurant.domain.entities;

import alararestaurant.errors.ErrorConstants;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 30, message = ErrorConstants.INVALID_DATA)
    private String name;

    @Column(name = "age", nullable = false)
    @Range(min = 15, max = 80, message = ErrorConstants.INVALID_DATA)
    private Integer age;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id",referencedColumnName = "id")
    @NotNull(message = ErrorConstants.INVALID_DATA)
    private Position position;

    @OneToMany(mappedBy = "employee",targetEntity = Order.class,cascade = CascadeType.ALL)
    private List<Order> order;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
