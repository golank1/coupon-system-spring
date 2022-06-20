package com.jb.coupon_system_spring.threads;

import com.jb.coupon_system_spring.repository.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class DeleteCoupons {
    private final CouponRepo couponRepo;

    /**
     * Asynchronous method that deletes expired coupons from the database every day at 00:00:00 GMT+3
     */
    @Async
    @Scheduled(cron="00 00 00 * * ?",zone = "Asia/Jerusalem")
    public void deleteCoupons(){
        Date now=new Date(System.currentTimeMillis());
        couponRepo.deleteExpiredCouponPurchase(now);
        couponRepo.deleteByEndDateBefore(now);
    }
}
