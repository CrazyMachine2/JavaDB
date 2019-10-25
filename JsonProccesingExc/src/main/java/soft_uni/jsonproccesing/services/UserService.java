package soft_uni.jsonproccesing.services;

import soft_uni.jsonproccesing.domain.dtos.UserSeedDto;
import soft_uni.jsonproccesing.domain.dtos.UserWithSoldProductsDto;
import soft_uni.jsonproccesing.domain.entities.User;

import java.util.List;

public interface UserService {
    void seedUsers(UserSeedDto[] userSeedDtos);
    List<UserWithSoldProductsDto> findUsersWithSoldProducts();
}
