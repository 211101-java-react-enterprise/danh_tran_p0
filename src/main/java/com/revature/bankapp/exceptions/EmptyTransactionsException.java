package com.revature.bankapp.exceptions;

public class EmptyTransactionsException extends RuntimeException {

    public EmptyTransactionsException(String msg) {
        super(msg);
    }
}
