package example.services;

import example.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void registerUser(User user);
}
