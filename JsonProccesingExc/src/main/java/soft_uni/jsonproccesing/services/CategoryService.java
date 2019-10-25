package soft_uni.jsonproccesing.services;

import soft_uni.jsonproccesing.domain.dtos.CategorySeedDto;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDto);
}
