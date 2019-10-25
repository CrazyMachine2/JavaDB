package softuni.springadvanced.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springadvanced.domain.entities.Shampoo;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo,Long> {
}
