package softuni.springinro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.springinro.entities.AgeRestriction;
import softuni.springinro.entities.Book;
import softuni.springinro.entities.EditionType;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName,String lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    @Query("select b from Book as b where b.price not between :lower and :higher")
    List<Book> searchBooksWithLowerAndHigherPrice(BigDecimal lower, BigDecimal higher);

    @Query("select b from Book as b where not substring(b.releaseDate,1,4) = :year")
    List<Book> searchBookNotReleasedInYear(String year);

    List<Book> findAllByTitleContaining(String searched);

    List<Book> findAllByAuthorLastNameStartsWith(String start);

    @Query("select count(b.id) from Book as b where length(b.title) > :number")
    List<Object[]> searchCountWithTitleLongerThan(int number);

    @Query("select a.firstName,a.lastName, sum (b.copies) as copies from Book as b inner join b.author as a group by a.firstName,a.lastName order by copies desc ")
    List<Object[]> findTotalCopiesWithAuthor();

    @Modifying
    @Query("update Book as b set b.copies = b.copies + :copies where b.releaseDate >= :date")
    int updateBooksReleasedAfterWithCopies(int copies,LocalDate date);

    @Modifying
    @Query("delete from Book as b where b.copies < :copies")
    int destroyBooksByCopiesLowerThan(int copies);

    @Transactional
    @Procedure(procedureName = "total_number_of_books_by_author")
    List<Object[]> callProcedure(@Param("first_name") String firstName,@Param("last_name") String lastName);

}
