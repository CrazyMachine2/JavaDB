package softuni.workshop.domain.dtos.jsonDtos.exportDto;

import com.google.gson.annotations.Expose;

public class CompanyJsonDto {

    @Expose
    private String name;

    public CompanyJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
