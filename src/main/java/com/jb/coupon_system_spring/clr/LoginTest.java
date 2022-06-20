package com.jb.coupon_system_spring.clr;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.exceptions.LoginException;
import com.jb.coupon_system_spring.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(4)
@RequiredArgsConstructor
public class LoginTest implements CommandLineRunner {
    private final LoginService loginService;

    @Override
    public void run(String... args) throws Exception {
        testLogin("admin@admin.com","admin",ClientType.ADMIN);
        testLogin("company0@test.com","company",ClientType.COMPANY);
        testLogin("customer0@test.com","customer",ClientType.CUSTOMER);
        testLogin("admin@admin.com","admin",ClientType.CUSTOMER);
        testLogin("exception@exception.com","exception",ClientType.COMPANY);
    }


    private void testLogin(String email, String password, ClientType clientType) {
        try {
            String token = loginService.login(email, password, clientType);
            System.out.println(clientType.getName() + " login:\n" + token);
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }
    }
}
