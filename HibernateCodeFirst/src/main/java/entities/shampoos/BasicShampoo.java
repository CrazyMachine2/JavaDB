package entities.shampoos;

import entities.ingredients.BasicIngredient;
import entities.labels.BasicLabel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shampoos")
public abstract class BasicShampoo implements Shampoo{

    @Id
    @GeneratedValue
    private long id;
    private BigDecimal price;
    private String brand;

    private Size size;

    @OneToOne(cascade = CascadeType.ALL)
    private BasicLabel label;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<BasicIngredient> ingredients;

    protected BasicShampoo() {
        this.ingredients = new HashSet<>();
    }

    public BasicShampoo(BigDecimal price, String brand, Size size, BasicLabel label) {
        this();
        this.price = price;
        this.brand = brand;
        this.size = size;
        this.label = label;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Set<BasicIngredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(Set<BasicIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
