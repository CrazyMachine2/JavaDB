package softuni.springinro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springinro.entities.Author;
import softuni.springinro.repositories.AuthorRepository;
import softuni.springinro.services.base.AuthorService;
import softuni.springinro.util.FileUtil;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final static String AUTHOR_FILE_PATH =
            "D:\\JavaSoftuni\\Hibernate\\SpringIntoExc\\src\\main\\resources\\files\\authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }


    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authors = this.fileUtil.fileContent(AUTHOR_FILE_PATH);

        for (String a : authors) {
            String[] data = a.split("\\s+");
            Author author = new Author();
            author.setFirstName(data[0]);
            author.setLastName(data[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public List<String> getNamesByFirstNameEndsWith(String end) {
        return this.authorRepository.findAllByFirstNameEndsWith(end)
                .stream()
                .map(a -> String.format("%s %s",a.getFirstName(),a.getLastName()))
                .collect(Collectors.toList());
    }

}
