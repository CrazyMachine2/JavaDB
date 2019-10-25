package soft_uni.jsonproccesing.services;

import soft_uni.jsonproccesing.domain.dtos.ProductInRangeDto;
import soft_uni.jsonproccesing.domain.dtos.ProductSeedDto;
import soft_uni.jsonproccesing.domain.dtos.ProductsSoldDto;
import soft_uni.jsonproccesing.domain.dtos.UserWithSoldProductsDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);
    List<ProductInRangeDto> findProductsInRange();
    List<UserWithSoldProductsDto> soldProducts();
}
