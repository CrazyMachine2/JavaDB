package softuni.springadvanced.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springadvanced.domain.entities.Label;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label,Long> {

}
