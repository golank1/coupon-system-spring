package com.jb.coupon_system_spring.beans;

public enum ErrorTypes {
    UNIQUE_FIELD("Value already been taken, please try again"),
    UNCHANGED_VALUE("Value cannot be changed"),
    WRONG_COMPANY("Invalid company for the coupon, try again"),
    BAD_LOGIN("Incorrect email or password, please try again"),
    UNAUTHORIZED_USER("Unauthorized user"),
    COMPANY_NOT_EXIST("Company does not exist, please try again"),
    CUSTOMER_NOT_EXIST("Customer does not exist, please try again"),
    COUPON_NOT_EXIST("Coupon does not exist, please try again"),
    COUPON_PURCHASED("Coupon already been purchased, cannot purchase again"),
    COUPON_EXPIRED("Coupon expired and cannot be purchased"),
    COUPON_AMOUNT("Coupon out of stock, cannot be purchased");

    private String message;

    public String getMessage(){
        return this.message;
    }

    ErrorTypes(String message) {
        this.message = message;
    }
}
