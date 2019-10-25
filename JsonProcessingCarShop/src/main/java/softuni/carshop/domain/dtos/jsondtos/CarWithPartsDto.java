package softuni.carshop.domain.dtos.jsondtos;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class CarWithPartsDto {
    @Expose
    private String make;

    @Expose
    private String model;

    @Expose
    private Long travelledDistance;

    @Expose
    private Set<PartExportDto> parts;

    public CarWithPartsDto() {
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

    public Set<PartExportDto> getParts() {
        return parts;
    }

    public void setParts(Set<PartExportDto> parts) {
        this.parts = parts;
    }
}
