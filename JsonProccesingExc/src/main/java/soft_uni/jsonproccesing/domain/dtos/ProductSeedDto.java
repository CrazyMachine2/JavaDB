package soft_uni.jsonproccesing.domain.dtos;

import com.google.gson.annotations.Expose;
import soft_uni.jsonproccesing.domain.entities.Category;
import soft_uni.jsonproccesing.domain.entities.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class ProductSeedDto implements Serializable {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private User buyer;
    @Expose
    private User seller;
    @Expose
    private Set<Category> categories;

    public ProductSeedDto() {
    }

    @NotNull(message = "Name cannot be null.")
    @Size(min = 3, message = "Name should be at least 3 symbols.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Price cannot be null.")
    @Min(value = 0, message = "Price cannot be negative .")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @NotNull(message = "Seller cannot be null.")
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    @NotNull(message = "Categories cannot be null.")
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
