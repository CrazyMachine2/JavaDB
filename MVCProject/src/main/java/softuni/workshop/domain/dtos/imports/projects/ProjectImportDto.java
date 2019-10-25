package softuni.workshop.domain.dtos.imports.projects;

import softuni.workshop.domain.dtos.imports.companies.CompanyImportDto;
import softuni.workshop.domain.entities.Company;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectImportDto {

    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement(name = "is-finished")
    private Boolean isFinished;
    @XmlElement
    private BigDecimal payment;
    @XmlElement(name = "start-date")
    private String startDate;
    @XmlElement
    private CompanyImportDto company;

    public ProjectImportDto() {
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

    public CompanyImportDto getCompany() {
        return company;
    }

    public void setCompany(CompanyImportDto company) {
        this.company = company;
    }
}
