package softuni.carshop.domain.dtos.jsondtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class CustomerTotalSalesDto {
    @Expose
    @SerializedName("fullName")
    private String name;

    @Expose
    private Integer boughtCars;

    @Expose
    private BigDecimal spentMoney;

    public CustomerTotalSalesDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Integer boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }
}
