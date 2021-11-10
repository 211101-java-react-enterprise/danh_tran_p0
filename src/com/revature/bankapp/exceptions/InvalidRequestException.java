package com.revature.bankapp.exceptions;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String msg) {
        super(msg);
    }
}
