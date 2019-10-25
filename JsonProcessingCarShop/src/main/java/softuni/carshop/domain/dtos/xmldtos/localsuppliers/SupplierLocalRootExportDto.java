package softuni.carshop.domain.dtos.xmldtos.localsuppliers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierLocalRootExportDto {

    @XmlElement(name = "supplier")
    private List<SupplierLocalExportDto> suppliers;

    public SupplierLocalRootExportDto() {
    }

    public List<SupplierLocalExportDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierLocalExportDto> suppliers) {
        this.suppliers = suppliers;
    }
}
