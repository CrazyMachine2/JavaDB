//package entities.sales;
//
//import javax.persistence.*;
//import java.sql.Date;
//
//@Entity
//@Table(name = "sale")
//public class Sale {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    private Product product;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id", referencedColumnName = "id")
//    private Customer customer;
//
//    @ManyToOne
//    @JoinColumn(name = "store_location_id", referencedColumnName = "id")
//    private StoreLocation storeLocation;
//
//    @Column(name = "date")
//    private Date date;
//
//    public Sale() {
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
//    public Product getProduct() {
//        return this.product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public Customer getCustomer() {
//        return this.customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public StoreLocation getStoreLocation() {
//        return this.storeLocation;
//    }
//
//    public void setStoreLocation(StoreLocation storeLocation) {
//        this.storeLocation = storeLocation;
//    }
//
//    public Date getDate() {
//        return this.date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//}
