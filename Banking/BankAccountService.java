package com.banking;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankAccountService {

    @Autowired
    private BankAccountRepository repository;

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    public Optional<BankAccount> getAccount(String accNo) {
        return repository.findById(accNo);
    }

    public BankAccount createAccount(String accNo, String name, double initialBalance) {
        BankAccount account = new BankAccount(accNo, name, initialBalance);
        return repository.save(account);
    }

    public boolean deposit(String accNo, double amount) {
        Optional<BankAccount> accountOpt = repository.findById(accNo);
        if (accountOpt.isPresent() && amount > 0) {
            BankAccount account = accountOpt.get();
            account.deposit(amount);
            repository.save(account);
            return true;
        }
        return false;
    }

    public boolean withdraw(String accNo, double amount) {
        Optional<BankAccount> accountOpt = repository.findById(accNo);
        if (accountOpt.isPresent()) {
            BankAccount account = accountOpt.get();
            if (account.withdraw(amount)) {
                repository.save(account);
                return true;
            }
        }
        return false;
    }

    public boolean transfer(String fromAccNo, String toAccNo, double amount) {
        Optional<BankAccount> fromAccountOpt = repository.findById(fromAccNo);
        Optional<BankAccount> toAccountOpt = repository.findById(toAccNo);
        
        if (fromAccountOpt.isPresent() && toAccountOpt.isPresent()) {
            BankAccount fromAccount = fromAccountOpt.get();
            BankAccount toAccount = toAccountOpt.get();
            
            if (fromAccount.transfer(toAccount, amount)) {
                repository.save(fromAccount);
                repository.save(toAccount);
                return true;
            }
        }
        return false;
    }
}
