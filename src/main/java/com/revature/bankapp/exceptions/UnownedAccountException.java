package com.revature.bankapp.exceptions;

public class UnownedAccountException extends RuntimeException{

    public UnownedAccountException(String msg) {
        super(msg);
    }
}
