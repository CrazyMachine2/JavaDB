package softuni.carshop.domain.dtos.xmldtos.carwithparts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartExportRootDto {

    @XmlElement(name = "part")
    private List<PartExportXmlDto> parts;

    public PartExportRootDto() {
    }

    public List<PartExportXmlDto> getParts() {
        return parts;
    }

    public void setParts(List<PartExportXmlDto> parts) {
        this.parts = parts;
    }
}
