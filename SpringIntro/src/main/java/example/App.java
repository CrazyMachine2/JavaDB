package example;

import example.models.Account;
import example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import example.services.AccountServiceImpl;
import example.services.UserServiceImpl;

import java.math.BigDecimal;

@Component
public class App implements CommandLineRunner {
    @Autowired
    public UserServiceImpl userService;
    @Autowired
    public AccountServiceImpl accountService;


    @Override
    public void run(String... args) throws Exception {

        try {


            User example = new User();
            example.setUsername("example");
            example.setAge(20);

            Account account = new Account();
            account.setBalance(new BigDecimal(25000));
            account.setUser(example);
            accountService.addAccount(account);

            example.getAccounts().add(account);
            userService.registerUser(example);

            accountService.withdrawMoney(new BigDecimal(20000), account.getId());
            accountService.transferMoney(new BigDecimal(20000), 1L);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
