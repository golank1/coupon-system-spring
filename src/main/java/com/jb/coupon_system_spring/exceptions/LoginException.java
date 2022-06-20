package com.jb.coupon_system_spring.exceptions;

public class LoginException extends Exception{
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
