package com.jb.coupon_system_spring.service.interfaces;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.exceptions.CompanyException;

import java.util.List;

public interface CompanyServiceInterface {
    public int addCoupon(Coupon coupon) throws CompanyException;
    public void updateCoupon(Coupon coupon) throws CompanyException;
    public void deleteCoupon(int couponId) throws CompanyException;
    public List<Coupon> allCompanyCoupons() throws CompanyException;
    public List<Coupon> allCompanyCouponsByCategory(Category category) throws CompanyException;
    public List<Coupon> allCompanyCouponsByPrice(double price) throws CompanyException;
    public Company companyDetails() throws CompanyException;

}
