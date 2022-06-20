package com.jb.coupon_system_spring.controller;

import com.jb.coupon_system_spring.service.GuestService;
import com.jb.coupon_system_spring.util.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {
    private final GuestService guestService;
    private final JWT jwt;


    @GetMapping("/allCoupons")
    public ResponseEntity<?> getAllCoupons(){
        return ResponseEntity.ok()
//                .header("Authorization", jwt.generateGuestToken())
                .body(guestService.getAllCoupons());
    }
}
