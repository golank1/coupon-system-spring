package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.repository.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final CouponRepo couponRepo;

    public List<Coupon> getAllCoupons(){
        return couponRepo.findAll();
    }
}
