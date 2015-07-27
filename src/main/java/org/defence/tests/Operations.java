package org.defence.tests;

/**
 * Created by root on 26.07.15.
 */
public class Operations {
    public static void transfer(Account acc1, Account acc2, int amount)
            throws InsufficientFundsException {
        if (acc1.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        synchronized (acc1) {
            synchronized (acc2) {
                acc1.withdrow(amount);
                acc2.deposit(amount);
            }
        }
    }

    public static void main(String[] args) throws InsufficientFundsException {
        final Account a = new Account(1000);
        final Account b = new Account(2000);

        new Thread(new Runnable() {
            public void run() {
                try {
                    transfer(a, b, 500);
                } catch (InsufficientFundsException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        transfer(b, a, 300);

        System.out.format("Account1: Balance = %d\n", a.getBalance());
        System.out.format("Account2: Balance = %d\n", b.getBalance());
    }
}
