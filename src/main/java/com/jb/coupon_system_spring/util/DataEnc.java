package com.jb.coupon_system_spring.util;

import org.jasypt.util.text.StrongTextEncryptor;

public class DataEnc {

    public static String setEncryptor(String data){
        StrongTextEncryptor encryptor = new StrongTextEncryptor();
        encryptor.setPassword("this is my key");
        return encryptor.encrypt(data);
    }

    public static String getEncryptor(String data){
        StrongTextEncryptor encryptor = new StrongTextEncryptor();
        encryptor.setPassword("this is my key");
        return encryptor.decrypt(data);
    }

}
