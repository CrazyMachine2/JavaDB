package softuni.carshop.domain.dtos.jsondtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Set;

public class CustomerOrderedDto {
    @Expose
    @SerializedName("Id")
    private Integer id;

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("BirthDate")
    private Date birthDate;

    @Expose
    @SerializedName("IsYoungDriver")
    private boolean isYoungDriver;

    @Expose
    @SerializedName("Sale")
    private Set<SaleOrderedDto> sale;

    public CustomerOrderedDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<SaleOrderedDto> getSale() {
        return sale;
    }

    public void setSale(Set<SaleOrderedDto> sale) {
        this.sale = sale;
    }
}
