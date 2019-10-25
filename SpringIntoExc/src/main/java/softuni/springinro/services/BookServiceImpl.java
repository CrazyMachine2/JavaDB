package softuni.springinro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springinro.entities.*;
import softuni.springinro.repositories.AuthorRepository;
import softuni.springinro.repositories.BookRepository;
import softuni.springinro.repositories.CategoryRepository;
import softuni.springinro.services.base.BookService;
import softuni.springinro.util.FileUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class BookServiceImpl implements BookService {
    private final static String BOOKS_FILE_PATH =
            "D:\\JavaSoftuni\\Hibernate\\SpringIntoExc\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if(this.bookRepository.count() != 0){
            return;
        }

        String[] books = this.fileUtil.fileContent(BOOKS_FILE_PATH);

        for (String s : books) {
            String[] params = s.split("\\s+");

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            LocalDate releaseDate = LocalDate.parse(params[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];

            StringBuilder title = new StringBuilder();
            for (int i = 5; i <= params.length - 1; i++) {
                title.append(params[i]).append(" ");
            }

            Book book = new Book();
            book.setAuthor(randomAuthor());
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(Integer.parseInt(params[2]));
            book.setPrice(new BigDecimal(params[3]));
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title.toString().trim());
            book.setCategories(randomCategories());

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> callProcedure(String firstName, String lastName) {
        return this.bookRepository.callProcedure(firstName,lastName)
                .stream()
                .map(o -> String.format("%s %s %s",o[0],o[1],o[2]))
                .collect(Collectors.toList());
    }

    @Override
    public int getNumberOfUpdatedBooksReleasedAfter(int copies,LocalDate date){
        return this.bookRepository.updateBooksReleasedAfterWithCopies(copies,date);
    }


    @Override
    public int getNumberOfRemovedBooksWithCopiesLowerThan(int copies){
        return this.bookRepository.destroyBooksByCopiesLowerThan(copies);
    }

    @Override
    public  List<String> getTotalBookCopiesByAuthor(){
        return this.bookRepository.findTotalCopiesWithAuthor()
                .stream()
                .map(o -> String.format("%s %s - %s",o[0],o[1],o[2]))
                .collect(Collectors.toList());
    }

    @Override
    public String countByTitleLongerThan(int number){
        List<String> count = this.bookRepository.searchCountWithTitleLongerThan(number)
                .stream()
                .map(o -> String.format("%s", o[0]))
                .collect(Collectors.toList());

        return count.get(0);
    }

    @Override
    public List<String> getTitleByAuthorLastNameStartingWith(String start){
        return this.bookRepository.findAllByAuthorLastNameStartsWith(start)
                .stream()
                .map(b -> String.format("%s (%s %s)",b.getTitle(),b.getAuthor().getFirstName(),b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTitleContaining(String searched){
        return this.bookRepository.findAllByTitleContaining(searched)
                .stream()
                .map(b -> String.format("%s",b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getBooksWithReleaseDateBefore(LocalDate date){
        return this.bookRepository.findAllByReleaseDateBefore(date)
                .stream()
                .map(b -> String.format("%s %s %s",b.getTitle(),b.getEditionType(),b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTitleBookNotReleasedIn(String year){
        return this.bookRepository.searchBookNotReleasedInYear(year)
                .stream()
                .map(b -> String.format("%s",b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public  List<String> getTitleByPriceLowerAndHigherThan(){
        BigDecimal lower = new BigDecimal("5");
        BigDecimal higher = new BigDecimal("40");

        return this.bookRepository.searchBooksWithLowerAndHigherPrice(lower,higher)
                .stream()
                .map(b -> String.format("%s - $%.2f",b.getTitle(),b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTitleWithGoldenEditionWithLessThan5000Copies() {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD,5000)
                .stream()
                .map(b -> String.format("%s",b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllTitlesWithAgeRestriction(AgeRestriction ageRestriction) {

        return this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .stream()
                .map(b -> String.format("%s", b.getTitle()))
                .collect(Collectors.toList());
    }




    @Override
    public List<String> findAllTitles() {
        LocalDate releaseDate = LocalDate.parse("31/12/2000", DateTimeFormatter.ofPattern("d/M/yyyy"));

       return this.bookRepository
                .findAllByReleaseDateAfter(releaseDate)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }


    @Override
    public List<String> findAllAuthorsWithBookCount(){
        return this.authorRepository.findAll()
                .stream()
                .sorted((a1,a2) -> a2.getBooks().size() - a1.getBooks().size())
                .map(a -> String.format("%s %s %d",a.getFirstName(),a.getLastName(),a.getBooks().size()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllGeorgePowellBooks() {
        List<String> books = this.bookRepository
                .findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc("George","Powell")
                .stream()
                .map(b -> String.format("%s %s %d",b.getTitle(),b.getReleaseDate(),b.getCopies()))
                .collect(Collectors.toList());


        return books;
    }



    @Override
    public List<String> findAllAuthors() {
        LocalDate releaseDate = LocalDate.parse("01/01/1990", DateTimeFormatter.ofPattern("d/M/yyyy"));

        return this.bookRepository.findAllByReleaseDateBefore(releaseDate)
                .stream()
                .map(b -> String.format("%s %s",b.getAuthor().getFirstName(),b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }


    private Author randomAuthor(){
        Random random = new Random();

        int index = random.nextInt((int) this.authorRepository.count()) +1;

        return this.authorRepository.getOne(index);
    }

    private Category randomCategory(){
        Random random = new Random();

        int index = random.nextInt((int) this.categoryRepository.count()) +1 ;

        return this.categoryRepository.getOne(index);
    }

    private Set<Category> randomCategories(){
        Set<Category> categories = new HashSet<>();

        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) + 1;

        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }
        return categories;
    }
}

















