package example.services;

import example.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import example.repositories.AccountRepository;

import java.math.BigDecimal;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The account does not exist."));

        if (account.getUser() == null) {
            throw new IllegalArgumentException("The account does not have a user.");
        }

        if (account.getBalance().subtract(money).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The account does not have sufficient funds.");
        }

        account.setBalance(account.getBalance().subtract(money));
        this.accountRepository.save(account);
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The account does not exist."));

        if (account.getUser() == null) {
            throw new IllegalArgumentException("The account does not have a user.");
        }

        if (money.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The transef amount cannot be negative.");
        }

        account.setBalance(account.getBalance().add(money));
        this.accountRepository.save(account);
    }

    @Override
    public void addAccount(Account account) {
        this.accountRepository.save(account);
    }
}
