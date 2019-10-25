package softuni.springadvanced.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.springadvanced.repository.LabelRepository;

@Controller
public class AppController implements CommandLineRunner {
    private final LabelRepository labelRepository;

    @Autowired
    public AppController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
