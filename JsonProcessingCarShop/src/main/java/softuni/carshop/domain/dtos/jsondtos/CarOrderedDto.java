package softuni.carshop.domain.dtos.jsondtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarOrderedDto {
    @Expose
    @SerializedName("Make")
    private String make;

    @Expose
    @SerializedName("Model")
    private String model;

    public CarOrderedDto() {
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
}
