package com.jb.coupon_system_spring.beans;

import org.springframework.stereotype.Component;


public enum ClientType {
    ADMIN("ADMIN"),
    COMPANY("COMPANY"),
    CUSTOMER("CUSTOMER");
//    GUEST("GUEST");

    private final String name;

    ClientType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
