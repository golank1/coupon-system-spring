package com.jb.coupon_system_spring.controller;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.beans.ErrorTypes;
import com.jb.coupon_system_spring.exceptions.CompanyException;
import com.jb.coupon_system_spring.exceptions.LoginException;
import com.jb.coupon_system_spring.service.CompanyService;
import com.jb.coupon_system_spring.util.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CompanyController {
    private final CompanyService companyService;
    private final JWT jwt;
    private final ClientType clientType=ClientType.COMPANY;

    /**
     * This method used to get the details of a company from the database.
     * @param token is an authorization token.
     * @return a ResponseEntity with a token in the header and the details of the company in the body.
     * @throws CompanyException if the company doesn't exist.
     * @throws LoginException  if the user is unauthorized.
     */
    @GetMapping("/Details")
    public ResponseEntity<?> companyDetails
            (@RequestHeader(name = "Authorization") String token)
            throws CompanyException, LoginException {
        jwt.checkClient(companyService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", companyService.getToken())
                .body(companyService.companyDetails());
    }

    /**
     * this method used to add new coupon to the database.
     * @param token is an authorization token.
     * @param coupon is the new coupon we want to add to the database.
     * @return a ResponseEntity with a token in the header.
     * @throws LoginException if the user is unauthorized
     * @throws CompanyException if the company doesn't exist.
     */
    @PostMapping("/addCoupon")
    public ResponseEntity<?> addNewCoupon
            (@RequestHeader(name = "Authorization")String token, @RequestBody Coupon coupon)
            throws LoginException, CompanyException {
        jwt.checkClient(companyService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", companyService.getToken())
                .body(companyService.addCoupon(coupon));

    }

    /**
     * this method used to update a coupon from the database.
     * @param token is an authorization token.
     * @param coupon is the coupon we want to update.
     * @return a ResponseEntity with a token in the header.
     * @throws CompanyException if the company doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @PutMapping("/updateCoupon")
    public ResponseEntity<?> updateCoupon
            (@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon)
            throws CompanyException, LoginException {
        jwt.checkClient(companyService,token,clientType);
        companyService.updateCoupon(coupon);
        return ResponseEntity.ok()
                .header("Authorization", companyService.getToken())
                .build();
    }

    /**
     * This methos used to delete coupon by id from the database.
     * @param token is an authorization token.
     * @param id is the id of the coupon we want to delete.
     * @return a ResponseEntity with a token in the header.
     * @throws CompanyException if the company doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @DeleteMapping("/deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon
            (@RequestHeader(name = "Authorization")String token,@PathVariable int id)
            throws CompanyException, LoginException {
        jwt.checkClient(companyService,token,clientType);
        companyService.deleteCoupon(id);
        return ResponseEntity.ok()
                .header("Authorization", companyService.getToken())
                .build();
    }

    /**
     * This method returns all coupons from the database.
     * @param token is an authorization token.
     * @return a ResponseEntity with a token in the header and a list of all coupons in the body.
     * @throws CompanyException if the company doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @GetMapping("/allCoupons")
    public ResponseEntity<?> allCoupons
            (@RequestHeader(name = "Authorization")String token)
            throws CompanyException, LoginException {
        jwt.checkClient(companyService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", companyService.getToken())
                .body(companyService.allCompanyCoupons());
    }

    /**
     * This method returns a list of company's coupons from a single category.
     * @param token is an authorization token.
     * @param category is the category we want the coupons to be filtered by.
     * @return a ResponseEntity with a token in the header and a list of all coupons filtered by category in the body.
     * @throws CompanyException if the company doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @GetMapping("/couponsByCategory/{category}")
    public ResponseEntity<?> couponsByCategory
            (@RequestHeader(name = "Authorization")String token,@PathVariable Category category)
            throws CompanyException, LoginException {
        jwt.checkClient(companyService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", companyService.getToken())
                .body(companyService.allCompanyCouponsByCategory(category));
    }

    /**
     * This method returns a list of company's coupons up to a maximum price.
     * @param token is an authorization token.
     * @param price is the maximum price we want the coupons to be filtered by.
     * @return a ResponseEntity with a token in the header
     * and a list of company's coupons up to a maximum price in the body.
     * @throws CompanyException if the company doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @GetMapping("/couponsByPrice/{price}")
    public ResponseEntity<?> couponsByPrice(
            @RequestHeader(name = "Authorization")String token,@PathVariable double price)
            throws CompanyException, LoginException {
        jwt.checkClient(companyService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", companyService.getToken())
                .body(companyService.allCompanyCouponsByPrice(price));
    }

}
