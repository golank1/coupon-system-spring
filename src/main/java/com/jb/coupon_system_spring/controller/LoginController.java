package com.jb.coupon_system_spring.controller;

import com.jb.coupon_system_spring.beans.UserData;
import com.jb.coupon_system_spring.exceptions.LoginException;
import com.jb.coupon_system_spring.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class LoginController {
    private final LoginService loginService;

    /**
     * This method generate new JWT token when the user logs in to the system.
     * The token is being used as an authorization token in the system.
     * @param userData contains the user's email, password and the ClientType of the user.
     * @return a ResponseEntity with a token in the header.
     * @throws LoginException if email or password are incorrect
     * or if the user is unauthorized (exp. Company wants to log in as a customer).
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserData userData) throws LoginException {
        return  ResponseEntity.ok()
                .header("Authorization",
                        loginService.login(userData.getUserEmail(), userData.getUserPassword(), userData.getUserType()))
                .build();
//        return ResponseEntity.ok()
//                .body(loginService.login(userData.getUserEmail(),userData.getUserPassword(),userData.getUserType()));
    }

}
