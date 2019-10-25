package softuni.springadvanced.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.springadvanced.domain.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
}
