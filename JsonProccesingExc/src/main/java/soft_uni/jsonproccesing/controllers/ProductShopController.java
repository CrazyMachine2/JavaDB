package soft_uni.jsonproccesing.controllers;

import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import soft_uni.jsonproccesing.domain.dtos.*;
import soft_uni.jsonproccesing.services.CategoryService;
import soft_uni.jsonproccesing.services.ProductService;
import soft_uni.jsonproccesing.services.UserService;
import soft_uni.jsonproccesing.util.FileUtil;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {
    private final static String USER_JSON_FILE_PATH =
            "D:\\JavaSoftuni\\Hibernate\\JsonProccesingExc\\src\\main\\resources\\users.json";

    private final static String CATEGORY_JSON_FILE_PATH =
            "D:\\JavaSoftuni\\Hibernate\\JsonProccesingExc\\src\\main\\resources\\categories.json";

    private final static String PRODUCT_JSON_FILE_PATH =
            "D:\\JavaSoftuni\\Hibernate\\JsonProccesingExc\\src\\main\\resources\\products.json";

    private final Gson gson;
    private final FileUtil fileUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public ProductShopController(Gson gson, FileUtil fileUtil, UserService userService, CategoryService categoryService, ProductService productService) {
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedUsers();
//        seedCategories();
//        seedProducts();

//        productsInRange();
        userSoldProducts();
    }

    private void userSoldProducts(){
        List<UserWithSoldProductsDto> usersWithSoldProducts = this.productService.soldProducts();

        String content = this.gson.toJson(usersWithSoldProducts);
        System.out.println(content);
    }

    private void productsInRange(){
        List<ProductInRangeDto> products = this.productService.findProductsInRange();
        String content = this.gson.toJson(products);
        System.out.println(content);
    }

    private void seedUsers() throws IOException {
        String content = this.fileUtil.fileContent(USER_JSON_FILE_PATH);

        UserSeedDto[] userSeedDtos = this.gson.fromJson(content, UserSeedDto[].class);

        this.userService.seedUsers(userSeedDtos);
    }

    private void seedCategories() throws IOException {
        String content = this.fileUtil.fileContent(CATEGORY_JSON_FILE_PATH);

        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(content, CategorySeedDto[].class);

        this.categoryService.seedCategories(categorySeedDtos);
    }

    private void seedProducts() throws IOException {
        String content = this.fileUtil.fileContent(PRODUCT_JSON_FILE_PATH);

        ProductSeedDto[] productSeedDtos = this.gson.fromJson(content,ProductSeedDto[].class);

        this.productService.seedProducts(productSeedDtos);
    }
}
