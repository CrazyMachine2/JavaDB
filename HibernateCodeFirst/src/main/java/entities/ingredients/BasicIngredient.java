package entities.ingredients;

import entities.shampoos.BasicShampoo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
@Inheritance
public abstract class BasicIngredient implements Ingredient{

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private BigDecimal price;

    @ManyToMany(targetEntity = BasicShampoo.class, mappedBy = "ingredients")
    private List<BasicShampoo> shampoos;


    protected BasicIngredient() {
        this.shampoos = new ArrayList<>();
    }

    public BasicIngredient(String name, BigDecimal price) {
        this();
        this.name = name;
        this.price = price;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public List<BasicShampoo> getShampoos() {
        return this.shampoos;
    }

    @Override
    public void setShampoos(List<BasicShampoo> shampoos) {
        this.shampoos = shampoos;
    }
}
