package com.jb.coupon_system_spring.advice;

import com.jb.coupon_system_spring.exceptions.CouponException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CouponAdvice {
    /**
     * This method check the exception for the coupons
     * @param e the exception
     * @return error detail for a coupon
     */
    @ExceptionHandler(value = {CouponException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCouponException(Exception e){
        return new ErrorDetail("Coupon Error",e.getMessage());
    }
}
