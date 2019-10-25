package alararestaurant.domain.dtos.importDtos.item;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ItemDto {

    @Expose
    String name;
    @Expose
    BigDecimal price;
    @Expose
    String category;

    public ItemDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
