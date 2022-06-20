package com.jb.coupon_system_spring.clr;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.exceptions.CompanyException;
import com.jb.coupon_system_spring.service.CompanyService;
import com.jb.coupon_system_spring.util.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Order(2)
@RequiredArgsConstructor
public class CompanyTest implements CommandLineRunner {
    private final CompanyService companyService;
    private final int ONE_DAY=1000*60*60*24;
    @Override
    public void run(String... args) throws Exception {
            addCoupons();
            updateCoupons();
            deleteCoupon();
            allCompanyCoupons();
            allCompanyCouponsByCategory();
            allCompanyCouponsByPrice();
            companyDetails();
            exceptionTest();
    }

    public void addCoupons()  {
        for (int counter = 1;counter <= 2;counter++){
            companyService.setClientId(counter);
            for (int i=0;i<3;i++){
                Coupon coupon= Coupon
                        .builder()
                        .companyId(counter)
                        .category(Category.ELECTRICITY)
                        .amount(100)
                        .description("coupon number "+(i+1))
                        .title("coupon title "+(i+1))
                        .price(Math.ceil(Math.random()*100+1))
                        .startDate(new Date(System.currentTimeMillis()))
                        .endDate(new Date(System.currentTimeMillis()+(int)(Math.random()*7+1)*ONE_DAY))
                        .image("https://ecommerceguide.com/wp-content/uploads/2016/01/coupon-main.jpg")
                        .build();
                try {
                    companyService.addCoupon(coupon);
                }
                catch (Exception err){
                    System.out.println(err.getMessage());
                }

            }
        }

    }

    public void updateCoupons()  {
        companyService.setClientId(1);
        Coupon coupon = companyService.getCouponRepo().getById(1);
        coupon.setDescription("update coupon");
        try {
            companyService.updateCoupon(coupon);
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteCoupon()  {
        companyService.setClientId(2);
        try {
            companyService.deleteCoupon(6);
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }

    public void allCompanyCoupons()  {
        companyService.setClientId(2);
        try {
            TablePrinter.print(companyService.allCompanyCoupons());
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }

    public void allCompanyCouponsByCategory()  {
        companyService.setClientId(1);
        try {
            TablePrinter.print(companyService.allCompanyCouponsByCategory(Category.ELECTRICITY));
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }

    public void allCompanyCouponsByPrice()  {
        companyService.setClientId(1);
        try {
            TablePrinter.print(companyService.allCompanyCouponsByPrice(70.0));
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }

    public void companyDetails()  {
        companyService.setClientId(1);
        try {
            TablePrinter.print(companyService.companyDetails());
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exceptionTest(){

        wrongClientID();
        CouponAddException();  // adding an expired coupon
        CouponUpdateException();
        deleteCouponException();
        allCompanyCouponsException();
        allCompanyCouponsByCategoryException();
        allCompanyCouponsByPriceException();
        companyDetailsException();



    }

    public void wrongClientID(){
        companyService.setClientId(5);

        Coupon coupon= Coupon
                .builder()
                .companyId(5)
                .category(Category.ELECTRICITY)
                .amount(100)
                .description("coupon number "+(1))
                .title("coupon title "+(1))
                .price(Math.random()*100+1)
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis()+(int)(Math.random()*7+1)*ONE_DAY))
                .image("https://ecommerceguide.com/wp-content/uploads/2016/01/coupon-main.jpg")
                .build();
        try {
            System.out.println(companyService.addCoupon(coupon));
        }
        catch (Exception err){
            System.out.println(err.getMessage());
        }
    }

    public void CouponAddException(){

        companyService.setClientId(1);

        Coupon coupon= Coupon
                .builder()
                .companyId(companyService.getClientId())
                .category(Category.ELECTRICITY)
                .amount(100)
                .description("expired coupon")
                .title("expired coupon")
                .price(Math.ceil(Math.random()*100+1))
                .startDate(new Date(System.currentTimeMillis()-(int)(Math.random()*7+2)*ONE_DAY))
                .endDate(new Date(System.currentTimeMillis()-ONE_DAY))
                .image("https://ecommerceguide.com/wp-content/uploads/2016/01/coupon-main.jpg")
                .build();

        Coupon coupon2= Coupon
                .builder()
                .companyId(companyService.getClientId())
                .category(Category.ELECTRICITY)
                .amount(0)
                .description("out of stock coupon")
                .title("out of stock coupon")
                .price(Math.ceil(Math.random()*100+1))
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis()+(int)(Math.random()*7+1)*ONE_DAY))
                .image("https://ecommerceguide.com/wp-content/uploads/2016/01/coupon-main.jpg")
                .build();

        try {
            System.out.println(companyService.addCoupon(coupon));
            System.out.println(companyService.addCoupon(coupon2));
        }
        catch (Exception err){
            System.out.println(err.getMessage());
        }
    }


    public  void CouponUpdateException(){
        companyService.setClientId(1);

        Coupon coupon= companyService.getCouponRepo().getById(2);
        coupon.setCompanyId(2);

        try {
            companyService.updateCoupon(coupon);
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteCouponException(){
            companyService.setClientId(1);
        try {
            companyService.deleteCoupon(66);
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }

    public void allCompanyCouponsException()  {
        companyService.setClientId(5);
        try {
            TablePrinter.print(companyService.allCompanyCoupons());
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }

    public void allCompanyCouponsByCategoryException()  {
        companyService.setClientId(7);
        try {
            TablePrinter.print(companyService.allCompanyCouponsByCategory(Category.ELECTRICITY));
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }

    public void allCompanyCouponsByPriceException()  {
        companyService.setClientId(1);
        try {
            TablePrinter.print(companyService.allCompanyCouponsByPrice(-100.0));
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }

    public void companyDetailsException()  {
        companyService.setClientId(7);
        try {
            TablePrinter.print(companyService.companyDetails());
        } catch (CompanyException e) {
            System.out.println(e.getMessage());
        }
    }







}
