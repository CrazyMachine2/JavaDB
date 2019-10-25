package softuni.workshop.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 20, message = "Fist name is not valid")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 20, message = "Last name is not valid")
    private String lastName;

    @Column(name= "age", nullable = false)
    @Min(value = 16, message = "Not enough experience")
    private Integer age;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    public Employee() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
