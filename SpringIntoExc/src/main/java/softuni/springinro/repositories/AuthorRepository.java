package softuni.springinro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springinro.entities.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {
    List<Author> findAllByFirstNameEndsWith(String end);
}
