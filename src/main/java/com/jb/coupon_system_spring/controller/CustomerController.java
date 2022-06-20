package com.jb.coupon_system_spring.controller;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.ErrorTypes;
import com.jb.coupon_system_spring.exceptions.CouponException;
import com.jb.coupon_system_spring.exceptions.CustomerException;
import com.jb.coupon_system_spring.exceptions.LoginException;
import com.jb.coupon_system_spring.service.CustomerService;
import com.jb.coupon_system_spring.util.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CustomerController {

    private final CustomerService customerService;
    private final JWT jwt;
    private final ClientType clientType=ClientType.CUSTOMER;

    /**
     /**
     * This method is used to purchase a Coupon.
     *
     * @param token    is an authorization token.
     * @param couponId is the id of the coupon chosen.
     * @return a ResponseEntity with a token in the header.
     * @throws CouponException due to invalid coupon(amount, date, already purchased).
     * @throws LoginException  due to invalid user.
     */
    @PostMapping("/purchaseCoupon/{couponId}")
    public ResponseEntity<?> addCouponPurchase
            (@RequestHeader(name = "Authorization") String token, @PathVariable int couponId)
            throws CouponException, LoginException {
        jwt.checkClient(customerService,token,clientType);
        customerService.purchaseCoupon(couponId);
        return ResponseEntity.ok()
                .header("Authorization", customerService.getToken())
                .build();
    }

    /**
     * This method returns all the customer's coupons.
     *
     * @param token is an authorization token.
     * @return a ResponseEntity with token in the header and a list of customer's coupons in the body.
     * @throws CustomerException due to invalid customer id.
     * @throws LoginException    due to invalid user.
     */
    @GetMapping("/customerCoupons")
    public ResponseEntity<?> getCustomerCoupons
            (@RequestHeader(name = "Authorization") String token)
            throws CustomerException, LoginException {
        jwt.checkClient(customerService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", customerService.getToken())
                .body(customerService.getCustomerCoupon());
    }

    /**
     * This method returns all the customer's coupons by category.
     *
     * @param token    is an authorization token.
     * @param category is the category selected to show coupons according to.
     * @return a ResponseEntity with token in the header and a list of customer's coupons by category in the body.
     * @throws CustomerException due to invalid customer id.
     * @throws LoginException    due to invalid user.
     */
    @GetMapping("/customerCouponsByCategory/{category}")
    public ResponseEntity<?> getCustomerCouponsByCategory
            (@RequestHeader(name = "Authorization") String token,@PathVariable Category category)
            throws CustomerException, LoginException {
        jwt.checkClient(customerService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", customerService.getToken())
                .body(customerService.getCustomerCouponByCategory(category));
    }

    /**
     * This method returns all customer's coupons by maximum price.
     *
     * @param token is an authorization token.
     * @param price is the maximum price of customer's coupons to show.
     * @return a ResponseEntity with a token in the header and a list of customer's coupons by max price in the body.
     * @throws CustomerException due to invalid customerId
     * @throws LoginException    due to invalid user.
     */
    @GetMapping("/customerCouponsByPrice/{price}")
    public ResponseEntity<?> getCustomerCouponsByPrice
            (@RequestHeader(name = "Authorization") String token,@PathVariable double price)
            throws CustomerException, LoginException {
        jwt.checkClient(customerService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", customerService.getToken())
                .body(customerService.getCustomerCouponByPrice(price));
    }

    /**
     * This method returns connected customer's details.
     *
     * @param token is an authorization token.
     * @return a ResponseEntity with a token in the header and customer's details in the body.
     * @throws CustomerException due to invalid customer id.
     * @throws LoginException    due to invalid user.
     */
    @GetMapping("/Details")
    public ResponseEntity<?> customerDetails
            (@RequestHeader(name = "Authorization") String token)
            throws CustomerException, LoginException {
        jwt.checkClient(customerService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", customerService.getToken())
                .body(customerService.getCustomerDetails());
    }

}