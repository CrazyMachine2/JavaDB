package softuni.springinro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springinro.entities.Category;
import softuni.springinro.repositories.CategoryRepository;
import softuni.springinro.services.base.CategoryService;
import softuni.springinro.util.FileUtil;

import java.io.File;
import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final String CATEGORIES_FILE_PATH =
            "D:\\JavaSoftuni\\Hibernate\\SpringIntoExc\\src\\main\\resources\\files\\categories.txt";

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategory() throws IOException {
        if(this.categoryRepository.count() != 0){
            return;
        }

        String[] categories = this.fileUtil.fileContent(CATEGORIES_FILE_PATH);

        for (String c : categories) {

            Category category = new Category();
            category.setName(c);

            this.categoryRepository.saveAndFlush(category);
        }
    }
}
