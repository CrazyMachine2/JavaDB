package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder sb = new StringBuilder();

        List<Category> categories = this.categoryRepository.findAll().stream()
                .sorted((c1, c2) -> {
                    int diff = c2.getItems().size() - c1.getItems().size();

                    if (diff == 0) {
                        BigDecimal sum1 = c1.getItems().stream().map(Item::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
                        BigDecimal sum2 = c2.getItems().stream().map(Item::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
                        diff = sum2.compareTo(sum1);
                    }

                    return diff;
                }).collect(Collectors.toList());

        for (Category category : categories) {
            sb.append(String.format("Category: %s", category.getName())).append(System.lineSeparator());

            for (Item item : category.getItems()) {
                sb.append(String.format("---Item Name: %s", item.getName())).append(System.lineSeparator());
                sb.append(String.format("---Item Price: %s", item.getPrice())).append(System.lineSeparator());
                sb.append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}