package soft_uni.jsonproccesing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.jsonproccesing.domain.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
