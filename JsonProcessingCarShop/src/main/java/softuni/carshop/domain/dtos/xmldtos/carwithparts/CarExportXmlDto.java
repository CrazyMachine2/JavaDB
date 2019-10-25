package softuni.carshop.domain.dtos.xmldtos.carwithparts;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportXmlDto {

    @XmlAttribute
    private String make;

    @XmlAttribute
    private String model;

    @XmlAttribute(name = "travelled-distance")
    private Long travelledDistance;

    @XmlElement
    private PartExportRootDto parts;

    public CarExportXmlDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public PartExportRootDto getParts() {
        return parts;
    }

    public void setParts(PartExportRootDto parts) {
        this.parts = parts;
    }
}
