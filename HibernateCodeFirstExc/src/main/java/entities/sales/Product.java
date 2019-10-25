//package entities.sales;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.util.Set;
//
//@Entity
//@Table(name = "product")
//public class Product {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "quantity")
//    private Double quantity;
//
//    @Column(name = "price")
//    private BigDecimal price;
//
//    @OneToMany(mappedBy = "product", targetEntity = Sale.class, cascade = CascadeType.ALL)
//    private Set<Sale> sales;
//
//
//    public Product() {
//    }
//
//    public int getId() {
//        return this.id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Double getQuantity() {
//        return this.quantity;
//    }
//
//    public void setQuantity(Double quantity) {
//        this.quantity = quantity;
//    }
//
//    public BigDecimal getPrice() {
//        return this.price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public Set<Sale> getSales() {
//        return this.sales;
//    }
//
//    public void setSales(Set<Sale> sales) {
//        this.sales = sales;
//    }
//}
