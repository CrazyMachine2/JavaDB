package softuni.workshop.domain.dtos.jsonDtos.exportDto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.List;

public class ProjectJsonDto {

    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private Boolean isFinished;
    @Expose
    private BigDecimal payment;
    @Expose
    private String startDate;
    @Expose
    private CompanyJsonDto company;
    @Expose
    private List<EmployeeJsonDto> employees;

    public ProjectJsonDto() {
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

    public CompanyJsonDto getCompany() {
        return company;
    }

    public void setCompany(CompanyJsonDto company) {
        this.company = company;
    }

    public List<EmployeeJsonDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeJsonDto> employees) {
        this.employees = employees;
    }
}
