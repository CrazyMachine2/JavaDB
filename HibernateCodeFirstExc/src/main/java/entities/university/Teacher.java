//package entities.university;
//
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.OneToMany;
//import java.util.Set;
//
//@Entity
//public class Teacher extends BaseUniversityEntity{
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "salary_per_hour")
//    private Double salaryPerHour;
//
//    @OneToMany(mappedBy = "teacher", targetEntity = Course.class)
//    private Set<Course> courses;
//
//    public String getEmail() {
//        return this.email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Double getSalaryPerHour() {
//        return this.salaryPerHour;
//    }
//
//    public void setSalaryPerHour(Double salaryPerHour) {
//        this.salaryPerHour = salaryPerHour;
//    }
//
//    public Set<Course> getCourses() {
//        return this.courses;
//    }
//
//    public void setCourses(Set<Course> courses) {
//        this.courses = courses;
//    }
//}
