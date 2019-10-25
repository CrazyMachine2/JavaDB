//package entities.university;
//
//import javax.persistence.*;
//import java.sql.Date;
//import java.util.Set;
//
//@Entity
//public class Course extends BaseEntity {
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "description")
//    private String description;
//
//    @Column(name = "start_date")
//    private Date startDate;
//
//    @Column(name = "end_date")
//    private Date endDate;
//
//    @Column(name = "credits")
//    private int credits;
//
//    @ManyToOne
//    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
//    private Teacher teacher;
//
//    @ManyToMany(mappedBy = "courses",targetEntity = Student.class)
//    private Set<Student> students;
//}
