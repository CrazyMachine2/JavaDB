//package entities.university;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//public class Student extends BaseUniversityEntity {
//
//    @Column(name = "average_grade")
//    private Double averageGrade;
//
//    @Column(name = "attendance")
//    private String attendance;
//
//    @ManyToMany
//            @JoinTable(name = "students_courses",
//            joinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn (name = "course_id",referencedColumnName = "id"))
//    Set<Course> courses;
//
//    public Double getAverageGrade() {
//        return this.averageGrade;
//    }
//
//    public void setAverageGrade(Double averageGrade) {
//        this.averageGrade = averageGrade;
//    }
//
//    public String getAttendance() {
//        return this.attendance;
//    }
//
//    public void setAttendance(String attendance) {
//        this.attendance = attendance;
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
