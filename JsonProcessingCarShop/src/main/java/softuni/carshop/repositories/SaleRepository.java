package softuni.carshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.carshop.domain.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Integer> {
}
