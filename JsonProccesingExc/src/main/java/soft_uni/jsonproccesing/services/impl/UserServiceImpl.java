package soft_uni.jsonproccesing.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.jsonproccesing.domain.dtos.UserSeedDto;
import soft_uni.jsonproccesing.domain.dtos.UserWithSoldProductsDto;
import soft_uni.jsonproccesing.domain.entities.User;
import soft_uni.jsonproccesing.repositories.UserRepository;
import soft_uni.jsonproccesing.services.UserService;
import soft_uni.jsonproccesing.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<UserWithSoldProductsDto> findUsersWithSoldProducts() {
        List<User> soldProducts = this.userRepository.getSoldProducts();
        System.out.println();

        return this.userRepository.getSoldProducts()
                .stream()
                .map(u -> this.modelMapper.map(u, UserWithSoldProductsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {

        for (UserSeedDto userSeedDto : userSeedDtos) {
            if(!this.validatorUtil.isValid(userSeedDto)){
                this.validatorUtil.violations(userSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            User user = this.modelMapper.map(userSeedDto,User.class);

            this.userRepository.saveAndFlush(user);
        }
    }
}
