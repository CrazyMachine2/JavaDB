//package entities.hospital;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Lob;
//import javax.persistence.Table;
//import java.util.Date;
//
//@Entity
//@Table(name = "patients")
//public class Patient extends BaseEntity {
//    @Column(name = "first_name")
//    private String firstName;
//
//    @Column(name = "last_name")
//    private String lastName;
//
//    @Column(name = "address")
//    private String address;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "date_of_birth")
//    private Date dateOfBirth;
//
//    @Lob
//    @Column(name = "picture",length = 100000)
//    private byte[] picture;
//
//    @Column(name = "medical_insurance")
//    private boolean medicalInsurance;
//
//    public Patient() {
//    }
//
//    public String getFirstName() {
//        return this.firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return this.lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getAddress() {
//        return this.address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
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
//    public Date getDateOfBirth() {
//        return this.dateOfBirth;
//    }
//
//    public void setDateOfBirth(Date dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public byte[] getPicture() {
//        return this.picture;
//    }
//
//    public void setPicture(byte[] picture) {
//        this.picture = picture;
//    }
//
//    public boolean isMedicalInsurance() {
//        return this.medicalInsurance;
//    }
//
//    public void setMedicalInsurance(boolean medicalInsurance) {
//        this.medicalInsurance = medicalInsurance;
//    }
//}
