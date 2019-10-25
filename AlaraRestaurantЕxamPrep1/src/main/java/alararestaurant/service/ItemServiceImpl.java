package alararestaurant.service;

import alararestaurant.domain.dtos.importDtos.item.ItemDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.errors.ErrorConstants;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.constants.ImportConstants;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ImportConstants.ITEMS_JSON);
    }

    @Override
    public String importItems(String items) throws IOException {
        StringBuilder sb = new StringBuilder();

        String content = this.readItemsJsonFile();
        ItemDto[] itemDtos = this.gson.fromJson(content, ItemDto[].class);

        for (ItemDto itemDto : itemDtos) {

            if (this.itemRepository.findItemByName(itemDto.getName()).isPresent()) {
                sb.append(ErrorConstants.INVALID_DATA).append(System.lineSeparator());
                continue;
            }

            Item item = this.modelMapper.map(itemDto, Item.class);
            Category category = this.categoryRepository.findCategoryByName(itemDto.getCategory()).orElse(null);

            if (category == null) {
                category = new Category();
                category.setName(itemDto.getCategory());

                if (!this.validationUtil.isValid(category)) {
                    String message = new ArrayList<>(this.validationUtil.violations(category)).get(0).getMessage();
                    sb.append(message).append(System.lineSeparator());
                    continue;
                }
            }

            item.setCategory(category);

            if(!this.validationUtil.isValid(item)){
                String message = new ArrayList<>(this.validationUtil.violations(item)).get(0).getMessage();
                sb.append(message).append(System.lineSeparator());
                continue;
            }

            this.itemRepository.saveAndFlush(item);
            sb.append(String.format("Record %s successfully imported.",item.getName())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
