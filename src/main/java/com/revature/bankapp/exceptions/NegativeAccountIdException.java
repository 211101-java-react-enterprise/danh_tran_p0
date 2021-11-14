package com.revature.bankapp.exceptions;

public class NegativeAccountIdException extends RuntimeException {

    public NegativeAccountIdException(String msg) {
        super(msg);
    }
}
