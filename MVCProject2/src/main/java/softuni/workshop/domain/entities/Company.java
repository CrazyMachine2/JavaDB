package softuni.workshop.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity{

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 15, message = "Name is not valid")
    private String name;

    @OneToMany(mappedBy = "company", targetEntity = Project.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Project> projects;

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
