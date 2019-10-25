package softuni.gameshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gameshop.domain.dtos.GameAddDto;
import softuni.gameshop.domain.entities.Game;
import softuni.gameshop.domain.entities.Role;
import softuni.gameshop.domain.entities.User;
import softuni.gameshop.repositories.GameRepository;
import softuni.gameshop.repositories.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private String loggedInUser;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String addGame(GameAddDto gameAddDto) {
        StringBuilder sb = new StringBuilder();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Game game = this.modelMapper.map(gameAddDto, Game.class);
        Set<ConstraintViolation<Object>> violations = validator.validate(game);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
            return sb.toString();
        }

        User user = this.userRepository.findByEmail(this.loggedInUser).orElse(null);

        if (!user.getRole().equals(Role.ADMIN)) {
            return sb.append(String.format("%s is not admin !", user.getFullName())).toString();
        }

        this.gameRepository.saveAndFlush(game);

        Set<Game> games = user.getGames();
        games.add(game);
        user.setGames(games);

        this.userRepository.saveAndFlush(user);

        sb.append(String.format("Added %s", game.getTitle()));
        return sb.toString();
    }

    @Override
    public void setLoggedInUser(String email) {
        this.loggedInUser = email;
    }

    @Override
    public void logOutUser() {
        this.loggedInUser = "";
    }
}
