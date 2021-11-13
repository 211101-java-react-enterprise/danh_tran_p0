package com.revature.bankapp.exceptions;

public class NegativeMoneyChargeException extends ArithmeticException {

    public NegativeMoneyChargeException(String msg) {
        super(msg);
    }
}
