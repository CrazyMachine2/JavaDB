package softuni.carshop.domain.dtos.xmldtos.ordercustomers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerRootOrderExportDto {

    @XmlElement(name = "customer")
    private List<CustomerOrderExportDto> customers;

    public CustomerRootOrderExportDto() {
    }

    public List<CustomerOrderExportDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerOrderExportDto> customers) {
        this.customers = customers;
    }
}
