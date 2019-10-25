package softuni.springinro.services.base;


import softuni.springinro.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    List<String> getNamesByFirstNameEndsWith(String end);
}
