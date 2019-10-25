package softuni.workshop.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends BaseEntity{

    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 15, message = "Username is not valid")
    private String name;

    @Column(name = "description", nullable = false)
    @Size(min = 5, max = 1000, message = "Description is not valid")
    private String description;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Column(name = "payment", nullable = false)
    @Min(value = 1500, message = "Payment is too low")
    private BigDecimal payment;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @OneToMany(mappedBy = "project", targetEntity = Employee.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Employee> employees;

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
