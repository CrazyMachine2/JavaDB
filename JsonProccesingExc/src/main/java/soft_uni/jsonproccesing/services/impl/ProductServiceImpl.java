package soft_uni.jsonproccesing.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import soft_uni.jsonproccesing.domain.dtos.ProductInRangeDto;
import soft_uni.jsonproccesing.domain.dtos.ProductSeedDto;
import soft_uni.jsonproccesing.domain.dtos.ProductsSoldDto;
import soft_uni.jsonproccesing.domain.dtos.UserWithSoldProductsDto;
import soft_uni.jsonproccesing.domain.entities.Category;
import soft_uni.jsonproccesing.domain.entities.Product;
import soft_uni.jsonproccesing.domain.entities.User;
import soft_uni.jsonproccesing.repositories.CategoryRepository;
import soft_uni.jsonproccesing.repositories.ProductRepository;
import soft_uni.jsonproccesing.repositories.UserRepository;
import soft_uni.jsonproccesing.services.ProductService;
import soft_uni.jsonproccesing.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public List<UserWithSoldProductsDto> soldProducts() {
        List<Product> soldProducts = this.productRepository.findByBuyerNotNullOrderBySellerLastNameAscSellerFirstNameAsc();
        System.out.println();

        return this.userRepository.getSoldProducts()
                .stream()
                .map(u -> this.modelMapper.map(u, UserWithSoldProductsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductInRangeDto> findProductsInRange() {
        BigDecimal lower = new BigDecimal(500);
        BigDecimal greater = new BigDecimal(1000);

        return this.productRepository.findByPriceLessThanOrPriceGreaterThanAndBuyerIsNull(lower,greater)
                .stream()
                .map(p -> this.modelMapper.map(p,ProductInRangeDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public void seedProducts(ProductSeedDto[] productSeedDtos) {
        for (ProductSeedDto productSeedDto : productSeedDtos) {
            productSeedDto.setSeller(this.getRandomSeller());
            productSeedDto.setBuyer(this.getRandomBuyer());
            productSeedDto.setCategories(this.getRandomCategories());

            if(!this.validatorUtil.isValid(productSeedDto)){
                this.validatorUtil.violations(productSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            Product product = this.modelMapper.map(productSeedDto, Product.class);

            this.productRepository.saveAndFlush(product);
        }
    }

    private User getRandomBuyer(){
        Random random = new Random();

        long id = random.nextInt((int)this.userRepository.count()-1) +1;

        if(id % 4 == 0){
            return null;
        }

        return this.userRepository.getOne(id);
    }

    private User getRandomSeller(){
        Random random = new Random();

        long id = random.nextInt((int)this.userRepository.count()-1) +1;

        return this.userRepository.getOne(id);
    }

    private Category getRandomCategory(){
        Random random = new Random();

        long id = random.nextInt((int)this.categoryRepository.count()-1) +1;

        return this.categoryRepository.findById(id).orElse(null);
    }

    private Set<Category> getRandomCategories(){
        Set<Category> categories = new HashSet<>();
        Random random = new Random();

        int index = random.nextInt((int) this.categoryRepository.count() -1) + 1;

        for (int i = 0; i < index; i++) {
            categories.add(getRandomCategory());
        }

        return categories;
    }
}
