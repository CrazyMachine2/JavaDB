package soft_uni.jsonproccesing.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.jsonproccesing.domain.dtos.CategorySeedDto;
import soft_uni.jsonproccesing.domain.entities.Category;
import soft_uni.jsonproccesing.domain.entities.User;
import soft_uni.jsonproccesing.repositories.CategoryRepository;
import soft_uni.jsonproccesing.services.CategoryService;
import soft_uni.jsonproccesing.util.ValidatorUtil;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDto) {
        for (CategorySeedDto seedDto : categorySeedDto) {
            if(!this.validatorUtil.isValid(seedDto)){
                this.validatorUtil.violations(seedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            Category category = this.modelMapper.map(seedDto,Category.class);

            this.categoryRepository.saveAndFlush(category);
        }
    }
}
