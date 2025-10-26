package com.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingSystemApplication implements CommandLineRunner {

    @Autowired
    private BankAccountRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(BankingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize with sample data
        repository.save(new BankAccount("101", "Alice", 5000.0));
        repository.save(new BankAccount("102", "Bob", 3000.0));
        repository.save(new BankAccount("103", "Charlie", 7500.0));
    }
}
