package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.beans.ErrorTypes;
import com.jb.coupon_system_spring.exceptions.CompanyException;
import com.jb.coupon_system_spring.service.interfaces.CompanyServiceInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Setter
@Getter
public class CompanyService extends ClientService implements CompanyServiceInterface {


    /**
     * This method adds new coupon to the database.
     *
     * @param coupon is the new coupon we want to add.
     * @return
     * @throws CompanyException if the company doesn't exist.
     */
    @Override
    public int addCoupon(Coupon coupon) throws CompanyException {
        if(!companyRepo.existsById(coupon.getCompanyId())){
            throw new CompanyException(ErrorTypes.COMPANY_NOT_EXIST.getMessage());
        }
        if(coupon.getCompanyId()!=this.clientId){
            throw new CompanyException(ErrorTypes.WRONG_COMPANY.getMessage());
        }
        couponRepo.save(coupon);
        Optional<Coupon> couponOptional=couponRepo.findById(coupon.getId());
        if(couponOptional.isPresent()){
            return couponOptional.get().getId();
        }else{
            throw new CompanyException("something went wrong");
        }

    }

    /**
     * This method update an existing coupon in the database.
     * @param coupon is the new coupon we want to update.
     * @throws CompanyException if the company doesn't exist.
     */
    @Override
    public void updateCoupon(Coupon coupon) throws CompanyException {
        if(couponRepo.existsById(coupon.getId())){
            if(!companyRepo.existsById(coupon.getId())){
                throw new CompanyException(ErrorTypes.COMPANY_NOT_EXIST.getMessage());
            } else if (coupon.getCompanyId()!=this.clientId) {
                throw new CompanyException(ErrorTypes.UNCHANGED_VALUE.getMessage());
            }else {
                couponRepo.save(coupon);
            }
        }else{
            throw new CompanyException(ErrorTypes.COUPON_NOT_EXIST.getMessage());
        }

    }

    /**
     * This method deletes an existing coupon from the database based on its id.
     * @param couponId is the id of the coupon we want to delete.
     * @throws CompanyException if the company doesn't exist.
     */
    @Override
    public void deleteCoupon(int couponId) throws CompanyException {
        if (couponRepo.existsById(couponId)) {
            if (couponRepo.getById(couponId).getCompanyId() == this.clientId) {
                couponRepo.deleteCouponPurchase(couponId);
                couponRepo.deleteById(couponId);
            } else {
                throw new CompanyException(ErrorTypes.UNAUTHORIZED_USER.getMessage());
            }
        } else {
            throw new CompanyException(ErrorTypes.COUPON_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method returns a list of all coupons from the database.
     * @return a list of all coupons from the database.
     * @throws CompanyException if the company doesn't exist.
     */
    @Override
    public List<Coupon> allCompanyCoupons() throws CompanyException {
        if (companyRepo.existsById(this.clientId)) {
            return couponRepo.findByCompanyId(this.clientId);
        } else {
            throw new CompanyException(ErrorTypes.COMPANY_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method returns a list of company's coupons from a single category from the database.
     * @param category is the category we want the coupons to be filtered by.
     * @return a list of all coupons filtered by category from the database.
     * @throws CompanyException if the company doesn't exist.
     */
    @Override
    public List<Coupon> allCompanyCouponsByCategory(Category category) throws CompanyException {
        if (companyRepo.existsById(this.clientId)) {
            return couponRepo.findByCompanyIdAndCategory(this.clientId, category);
        } else {
            throw new CompanyException(ErrorTypes.COMPANY_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method returns a list of company's coupons up to a maximum price from the database.
     * @param price is the maximum price we want the coupons to be filtered by.
     * @return a list of all company's coupons up to a maximum price from the database.
     * @throws CompanyException if the company doesn't exist.
     */
    @Override
    public List<Coupon> allCompanyCouponsByPrice(double price) throws CompanyException {
        if (companyRepo.existsById(this.clientId)) {
            return couponRepo.findByCompanyIdAndPriceLessThanEqual(this.clientId, price);
        } else {
            throw new CompanyException(ErrorTypes.COMPANY_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method used to get the details of a company from the database.
     * @return the details of the company.
     * @throws CompanyException if the company doesn't exist.
     */
    @Override
    public Company companyDetails() throws CompanyException {
        Optional<Company> company = companyRepo.findById(this.clientId);
        if (company.isPresent()) {
            return company.get();
        } else {
            throw new CompanyException(ErrorTypes.COMPANY_NOT_EXIST.getMessage());
        }
    }
}
