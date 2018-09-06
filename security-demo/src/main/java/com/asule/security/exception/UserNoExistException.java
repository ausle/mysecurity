package com.asule.security.exception;

public class UserNoExistException extends RuntimeException{

    public UserNoExistException(String message) {
        super(message);
    }
}
