package com.revature.bankapp.exceptions;

public class OverChargeException extends RuntimeException{

    public OverChargeException(String msg) {
        super(msg);
    }
}
