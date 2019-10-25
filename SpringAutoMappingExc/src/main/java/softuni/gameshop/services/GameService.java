package softuni.gameshop.services;

import softuni.gameshop.domain.dtos.GameAddDto;

public interface GameService {
    String addGame(GameAddDto gameAddDto);
    void setLoggedInUser(String email);
    void logOutUser();
}
