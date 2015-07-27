package org.defence.tests;

/**
 * Created by root on 26.07.15.
 */
public class InsufficientFundsException extends Exception {
    @Override
    public String getMessage() {
        return "Нехватает средств на счете для списания";
    }
}
