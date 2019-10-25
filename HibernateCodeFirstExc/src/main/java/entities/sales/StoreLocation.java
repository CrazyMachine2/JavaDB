//package entities.sales;
//
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "store_location")
//public class StoreLocation {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name = "location_name")
//    private String locationName;
//
//    @OneToMany(mappedBy = "storeLocation", targetEntity = Sale.class, cascade = CascadeType.ALL)
//    private Set<Sale> sales;
//
//    public StoreLocation() {
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
//    public String getLocationName() {
//        return this.locationName;
//    }
//
//    public void setLocationName(String locationName) {
//        this.locationName = locationName;
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
