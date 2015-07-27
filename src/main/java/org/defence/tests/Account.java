package org.defence.tests;

/**
 * Created by root on 26.07.15.
 */
public class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public void withdrow(int amount) {
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
