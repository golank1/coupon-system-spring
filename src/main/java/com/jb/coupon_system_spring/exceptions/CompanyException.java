package com.jb.coupon_system_spring.exceptions;

public class CompanyException extends Exception {
    public CompanyException() {
        super("Company exception !");
    }

    public CompanyException(String message) {
        super(message);
    }
}
