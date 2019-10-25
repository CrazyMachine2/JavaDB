package soft_uni.jsonproccesing.domain.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class UserWithSoldProductsDto {
    @Expose
    @SerializedName("firstName")
    private String sellerFirstName;
    @Expose
    @SerializedName("lastName")
    private String sellerLastName;
    @Expose
    @SerializedName("soldProducts")
    private Set<ProductsSoldDto> sellerSoldProducts;

    public UserWithSoldProductsDto() {
    }

    public String getSellerFirstName() {
        return sellerFirstName;
    }

    public void setSellerFirstName(String sellerFirstName) {
        this.sellerFirstName = sellerFirstName;
    }

    public String getSellerLastName() {
        return sellerLastName;
    }

    public void setSellerLastName(String sellerLastName) {
        this.sellerLastName = sellerLastName;
    }

    public Set<ProductsSoldDto> getSellerSoldProducts() {
        return sellerSoldProducts;
    }

    public void setSellerSoldProducts(Set<ProductsSoldDto> sellerSoldProducts) {
        this.sellerSoldProducts = sellerSoldProducts;
    }
}
