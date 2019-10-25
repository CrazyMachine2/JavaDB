package example.services;

import example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import example.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
   @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(User user) {
        if(this.userRepository.existsById(user.getId())){
            throw new IllegalArgumentException("User already exists!");
        }
        this.userRepository.save(user);
    }
}
