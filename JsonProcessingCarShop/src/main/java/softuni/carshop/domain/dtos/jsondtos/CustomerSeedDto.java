package softuni.carshop.domain.dtos.jsondtos;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class CustomerSeedDto {
    @Expose
    private String name;

    @Expose
    private Date birthDate;

    @Expose
    private boolean isYoungDriver;

    public CustomerSeedDto() {
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
}
