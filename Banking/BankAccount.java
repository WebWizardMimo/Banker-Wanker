package com.banking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    private String accNo;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private double balance;

    // Default constructor for JPA
    public BankAccount() {}

    public BankAccount(String accNo, String name, double balance) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (balance >= amount && amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(BankAccount other, double amount) {
        if (balance >= amount && amount > 0) {
            balance -= amount;
            other.balance += amount;
            return true;
        }
        return false;
    }

    // Getters and Setters
    public String getAccNo() { return accNo; }
    public void setAccNo(String accNo) { this.accNo = accNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
