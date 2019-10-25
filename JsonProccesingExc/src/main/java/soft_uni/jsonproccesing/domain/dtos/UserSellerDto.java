package soft_uni.jsonproccesing.domain.dtos;

import com.google.gson.annotations.Expose;

public class UserSellerDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;

    public UserSellerDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
