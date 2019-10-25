package softuni.carshop.domain.dtos.xmldtos.carwithparts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportRootDto {

    @XmlElement(name = "car")
    private List<CarExportXmlDto> cars;

    public CarExportRootDto() {
    }

    public List<CarExportXmlDto> getCars() {
        return cars;
    }

    public void setCars(List<CarExportXmlDto> cars) {
        this.cars = cars;
    }
}
