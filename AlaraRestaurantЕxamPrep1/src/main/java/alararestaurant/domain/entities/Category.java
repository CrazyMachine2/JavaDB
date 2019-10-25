package alararestaurant.domain.entities;

import alararestaurant.errors.ErrorConstants;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 30, message = ErrorConstants.INVALID_DATA)
    private String name;

    @OneToMany(mappedBy = "category",targetEntity = Item.class, cascade = CascadeType.ALL)
    private List<Item> items;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
