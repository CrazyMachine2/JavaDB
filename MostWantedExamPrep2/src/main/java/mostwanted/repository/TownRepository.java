package mostwanted.repository;

import mostwanted.domain.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town,Integer> {
    Optional<Town> findTownByName(String name);

    @Query(value = "select t.name, count(r.id) as counts from most_wanted_db.towns as t join most_wanted_db.racers as r on t.id = r.town_id " +
            "group by t.name order by counts desc, t.name", nativeQuery = true)
    Object[] getAllRacingTowns();
}
