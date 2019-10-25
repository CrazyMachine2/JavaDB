//package entities.gringotts;
//
//import org.hibernate.annotations.Check;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//
//@Entity
//@Table(name = "wizard_deposits")
//@Check(constraints = "age >= 0")
//public class WizardDeposit {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name = "first_name", length = 50)
//    private String firstName;
//
//    @Column(name = "last_name", length = 60, nullable = false)
//    private String lastName;
//
//    @Column(name = "notes", length = 1000)
//    private String notes;
//
//    @Column(name = "age", nullable = false)
//    private int age;
//
//    @Column(name = "magic_wand_creator", length = 100)
//    private String magicWandCreator;
//
//    @Column(name = "magic_wand_size")
//    private int magicWandSize;
//
//    @Column(name = "deposit_group", length = 20)
//    private String depositGroup;
//
//    @Column(name = "deposit_start_date")
//    private Timestamp depositStartDate;
//
//    @Column(name = "deposit_amount")
//    private double depositAmount;
//
//    @Column(name = "deposit_interest")
//    private double depositInterest;
//
//    @Column(name = "deposit_charge")
//    private  double depositCharge;
//
//    @Column(name = "deposit_expiration_date")
//    private Timestamp depositExpirationDate;
//
//    @Column(name = "is_deposit_expired")
//    private boolean isDepositExpired;
//}
