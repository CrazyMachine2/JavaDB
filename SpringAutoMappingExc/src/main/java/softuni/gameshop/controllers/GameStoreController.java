package softuni.gameshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.gameshop.domain.dtos.GameAddDto;
import softuni.gameshop.domain.dtos.UserLoginDto;
import softuni.gameshop.domain.dtos.UserRegisterDto;
import softuni.gameshop.services.GameService;
import softuni.gameshop.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true){
            String[] data = scanner.nextLine().split("\\|");

            switch (data[0]){
                case "RegisterUser":
                    UserRegisterDto userRegisterDto = new UserRegisterDto(data[1],data[2],data[3],data[4]);
                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;
                case "LoginUser":
                    UserLoginDto userLoginDto = new UserLoginDto(data[1],data[2]);
                    System.out.println(this.userService.loginUser(userLoginDto));
                    this.gameService.setLoggedInUser(userLoginDto.getEmail());
                    break;
                case "Logout":
                    System.out.println(this.userService.logOutUser());
                    this.gameService.logOutUser();
                    break;
                case "AddGame":

                    GameAddDto gameAddDto = new GameAddDto(
                            data[1],data[4],data[5], Double.parseDouble(data[3]),
                            new BigDecimal(data[2]), data[6], LocalDate.parse(data[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    );
                    System.out.println(this.gameService.addGame(gameAddDto));
                    break;
                case "EditGame":
                    break;
                case "DeleteGame":
                    break;
            }
        }
    }
}
