package soft_uni.jsonproccesing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.jsonproccesing.domain.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByPriceLessThanOrPriceGreaterThanAndBuyerIsNull(BigDecimal lower, BigDecimal greater);

    List<Product> findByBuyerNotNullOrderBySellerLastNameAscSellerFirstNameAsc();
}
