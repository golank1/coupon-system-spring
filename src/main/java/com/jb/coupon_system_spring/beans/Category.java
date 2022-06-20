package com.jb.coupon_system_spring.beans;

import javax.persistence.Entity;

public enum Category {
    ELECTRICITY("ELECTRICITY"),
    GAMING("GAMING"),
    MOBILE("MOBILE"),
    FOOD("FOOD"),
    HOME("HOME"),
    FASHION("FASHION"),
    COSMETICS("COSMETICS"),
    PHARMACY("PHARMACY"),
    PETS("PETS"),
    TOURISM("TOURISM"),
    OUTDOOR("OUTDOOR"),
    RESTAURANTS("RESTAURANTS");

    private String name;
    Category(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
}
