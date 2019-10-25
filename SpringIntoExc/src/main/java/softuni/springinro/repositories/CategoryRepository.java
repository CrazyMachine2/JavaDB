package softuni.springinro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springinro.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
