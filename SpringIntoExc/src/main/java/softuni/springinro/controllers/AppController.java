package softuni.springinro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.springinro.entities.AgeRestriction;
import softuni.springinro.services.base.AuthorService;
import softuni.springinro.services.base.BookService;
import softuni.springinro.services.base.CategoryService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Scanner;

@Controller
public class AppController implements CommandLineRunner {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public AppController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

//    this.authorService.seedAuthors();
//    this.categoryService.seedCategory();
//    this.bookService.seedBooks();

//    this.bookService.findAllTitles().forEach(System.out::println);
//    this.bookService.findAllAuthorsWithBookCount().forEach(System.out::println);
//    this.bookService.findAllGeorgePowellBooks().forEach(System.out::println);

        /*Problem 1*/
//        printBookTitlesByAgeRestriction(scanner);

        /*Problem 2*/
//        goldenBooks();

        /*Problem 3*/
//        booksByPrice();

        /*Problem 4*/
//        notReleasedBooks(scanner);

        /*Problem 5*/
//        booksReleasedBeforeDate(scanner);

        /*Problem 6*/
//        authorSearch(scanner);

        /*Problem 7*/
//        booksSearch(scanner);

        /*Problem 8*/
//      bookTitlesSearch(scanner);

        /*Problem 9*/
//        countBooks(scanner);

        /*Problem 10*/
//        totalBookCopies();

        /*Problem 12*/
//        increaseBookCopies(scanner);

        /*Problem 13*/
//        removeBooks(scanner);

        procedure();
    }

    private void printBookTitlesByAgeRestriction(Scanner scanner) {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(scanner.nextLine().toUpperCase());

        this.bookService.getAllTitlesWithAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    private void goldenBooks() {
        this.bookService.getTitleWithGoldenEditionWithLessThan5000Copies()
                .forEach(System.out::println);
    }

    private void booksByPrice() {
        this.bookService.getTitleByPriceLowerAndHigherThan()
                .forEach(System.out::println);
    }

    private void notReleasedBooks(Scanner scanner) {
        String year = scanner.nextLine();

        this.bookService.getTitleBookNotReleasedIn(year)
                .forEach(System.out::println);
    }

    private void booksReleasedBeforeDate(Scanner scanner) {
        String date = scanner.nextLine().replace("-", "/");
        LocalDate releaseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));

        this.bookService.getBooksWithReleaseDateBefore(releaseDate)
                .forEach(System.out::println);
    }

    private void authorSearch(Scanner scanner) {
        String end = scanner.nextLine();

        this.authorService.getNamesByFirstNameEndsWith(end)
                .forEach(System.out::println);
    }

    private void booksSearch(Scanner scanner) {
        String searched = scanner.nextLine();
        this.bookService.getTitleContaining(searched)
                .forEach(System.out::println);
    }

    private void bookTitlesSearch(Scanner scanner) {

        this.bookService.getTitleByAuthorLastNameStartingWith(scanner.nextLine())
                .forEach(System.out::println);
    }

    private void countBooks(Scanner scanner) {
        String count = this.bookService.countByTitleLongerThan(Integer.parseInt(scanner.nextLine()));
        System.out.println(count);
    }

    private void totalBookCopies(){
        this.bookService.getTotalBookCopiesByAuthor()
                .forEach(System.out::println);
    }

    private void increaseBookCopies(Scanner scanner){
        String date = scanner.nextLine().replace(" ","-");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd-MMM-yyyy")
                .toFormatter(Locale.UK);
        LocalDate releaseDate = LocalDate.parse(date, formatter);

        int copies = Integer.parseInt(scanner.nextLine());

        int n = this.bookService.getNumberOfUpdatedBooksReleasedAfter(copies, releaseDate);

        System.out.println("Number of time increased: " + n * copies);
    }

    private void removeBooks(Scanner scanner){
        int removedBooks = this.bookService.getNumberOfRemovedBooksWithCopiesLowerThan(Integer.parseInt(scanner.nextLine()));

        System.out.println("Numbers of books deleted: " + removedBooks);
    }

    private void procedure(){
        this.bookService.callProcedure("Amanda","Rice")
                .forEach(System.out::println);
    }
}
