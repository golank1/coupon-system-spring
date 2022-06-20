package com.jb.coupon_system_spring.exceptions;

public class AdminException extends Exception{
    public AdminException() {
        super("General admin exception");
    }

    public AdminException(String message) {
        super(message);
    }
}
