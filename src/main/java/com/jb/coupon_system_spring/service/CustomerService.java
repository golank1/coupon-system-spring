package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.beans.ErrorTypes;
import com.jb.coupon_system_spring.exceptions.CouponException;
import com.jb.coupon_system_spring.exceptions.CustomerException;
import com.jb.coupon_system_spring.service.interfaces.CustomerServiceInterFace;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Setter
@Getter
public class CustomerService extends ClientService implements CustomerServiceInterFace {


    /**
     * This method is used to purchase a coupon.
     *
     * @param couponId is the id of the coupon to purchase.
     * @throws CouponException due to invalid coupon(amount, date, already purchased).
     */
    @Override
    public void purchaseCoupon(int couponId) throws CouponException {
        Optional<Coupon> coupon = couponRepo.findById(couponId);
        if (coupon.isPresent()) {
            if (coupon.get().getAmount() == 0) {
                throw new CouponException(ErrorTypes.COUPON_AMOUNT.getMessage());
            }
            if (coupon.get().getEndDate().before(new Date(System.currentTimeMillis()))) {
                throw new CouponException(ErrorTypes.COUPON_EXPIRED.getMessage());
            }
            if(couponRepo.isCouponPurchased(this.clientId,couponId)==1){
                throw new CouponException(ErrorTypes.COUPON_PURCHASED.getMessage());
            }
            couponRepo.addCouponPurchase(this.clientId, coupon.get().getId());
            coupon.get().setAmount(coupon.get().getAmount() - 1);
            couponRepo.save(coupon.get());

        } else {
            throw new CouponException(ErrorTypes.COUPON_NOT_EXIST.getMessage());

        }
    }

    /**
     * This method returns a list of customer's coupons by id.
     *
     * @return a list of customer's coupons by id.
     * @throws CustomerException due to invalid customer id.
     */
    @Override
    public List<Coupon> getCustomerCoupon() throws CustomerException {
        if (customerRepo.existsById(this.clientId)) {
            return couponRepo.findCouponsByCustomerId(this.clientId);
        } else {
            throw new CustomerException(ErrorTypes.CUSTOMER_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method returns a list of customer's coupons by category.
     *
     * @param category is the category selected to show coupons according to.
     * @return a list of customer's coupons by category.
     * @throws CustomerException due to invalid customer id.
     */
    @Override
    public List<Coupon> getCustomerCouponByCategory(Category category) throws CustomerException {
        if(customerRepo.existsById(this.clientId)) {
            return couponRepo.findCouponsByCategoryAndCustomerId(category, this.clientId);
        }else {
            throw new CustomerException(ErrorTypes.CUSTOMER_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method returns a list of customer's coupons by maximum price.
     *
     * @param price is the maximum price of customer's coupons to show.
     * @return a list of customer's coupons by max price.
     * @throws CustomerException due to invalid customer id.
     */
    @Override
    public List<Coupon> getCustomerCouponByPrice(double price) throws CustomerException {
        if (customerRepo.existsById(this.clientId)) {
            return couponRepo.findCouponsByCustomerIdUpToPrice(this.clientId, price);
        } else {
            throw new CustomerException(ErrorTypes.CUSTOMER_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method returns the connected customer's details.
     *
     * @return connected customer's details.
     * @throws CustomerException due to invalid customer id.
     */
    @Override
    public Customer getCustomerDetails() throws CustomerException {
        Optional<Customer> customer = customerRepo.findById(this.clientId);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new CustomerException(ErrorTypes.CUSTOMER_NOT_EXIST.getMessage());
        }
    }
}
