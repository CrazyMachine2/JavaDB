package softuni.carshop.domain.dtos.xmldtos.toyotacars;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarToyotaExportRootDto {

    @XmlElement(name = "car")
    private List<CarToyotaExportDto> cars;

    public CarToyotaExportRootDto() {
    }

    public List<CarToyotaExportDto> getCars() {
        return cars;
    }

    public void setCars(List<CarToyotaExportDto> cars) {
        this.cars = cars;
    }
}
