package softuni.gameshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.gameshop.domain.entities.Game;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {
}
