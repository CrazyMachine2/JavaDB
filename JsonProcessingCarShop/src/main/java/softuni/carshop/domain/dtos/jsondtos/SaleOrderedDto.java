package softuni.carshop.domain.dtos.jsondtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleOrderedDto {
    @Expose
    @SerializedName("Car")
    private CarOrderedDto car;

    @Expose
    @SerializedName("DiscountPercentage")
    private Integer discountPercentage;

    public SaleOrderedDto() {
    }

    public CarOrderedDto getCar() {
        return car;
    }

    public void setCar(CarOrderedDto car) {
        this.car = car;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
