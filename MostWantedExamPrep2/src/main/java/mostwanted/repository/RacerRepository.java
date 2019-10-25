package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RacerRepository extends JpaRepository<Racer,Integer> {
    Optional<Racer> findRacerByName(String name);

    @Query("select r from Racer r where r.cars.size > 0 order by r.cars.size desc, r.name")
    List<Racer> findAllRacingCars();
}
