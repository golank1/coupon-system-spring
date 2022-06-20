package com.jb.coupon_system_spring.exceptions;

public class CustomerException extends Exception{
    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }
}
