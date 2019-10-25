package softuni.springinro.services.base;

import org.springframework.stereotype.Service;
import softuni.springinro.entities.AgeRestriction;
import softuni.springinro.entities.EditionType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;
    List<String> findAllTitles();
    List<String> findAllAuthors();
    List<String> findAllAuthorsWithBookCount();
    List<String> findAllGeorgePowellBooks();
    List<String> getAllTitlesWithAgeRestriction(AgeRestriction ageRestriction);
    List<String> getTitleWithGoldenEditionWithLessThan5000Copies();
    List<String> getTitleByPriceLowerAndHigherThan();
    List<String> getTitleBookNotReleasedIn(String year);
    List<String> getBooksWithReleaseDateBefore(LocalDate date);
    List<String> getTitleContaining(String searched);
    List<String> getTitleByAuthorLastNameStartingWith(String start);
    String countByTitleLongerThan(int number);
    List<String> getTotalBookCopiesByAuthor();
    int getNumberOfRemovedBooksWithCopiesLowerThan(int copies);
    int getNumberOfUpdatedBooksReleasedAfter(int copies,LocalDate date);
    List<String> callProcedure(String firstName,String lastName);
}
