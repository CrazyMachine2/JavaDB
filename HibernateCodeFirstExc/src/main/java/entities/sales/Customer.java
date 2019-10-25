//package entities.sales;
//
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "customer")
//public class Customer {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "credit_card_number")
//    private String creditCardNumber;
//
//    @OneToMany(mappedBy = "customer", targetEntity = Sale.class, cascade = CascadeType.ALL)
//    private Set<Sale> sales;
//
//    public Customer() {
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
//    public String getEmail() {
//        return this.email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCreditCardNumber() {
//        return this.creditCardNumber;
//    }
//
//    public void setCreditCardNumber(String creditCardNumber) {
//        this.creditCardNumber = creditCardNumber;
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
