package com.jb.coupon_system_spring.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private String userEmail;
    private String userPassword;
    private ClientType userType;
}
