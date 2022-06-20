package com.jb.coupon_system_spring.repository;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO customers_vs_coupons (customer_id, coupon_id) values (?1, ?2)", nativeQuery = true)
    void addCouponPurchase(int customerId, int couponId);

    List<Coupon> findByCompanyIdAndCategory(int companyId, Category categoryId);

    List<Coupon> findByCompanyId(int companyId);

    List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double price);

    @Query(value = "SELECT coupons.*" +
            " FROM coupons, customers_vs_coupons" +
            " WHERE customers_vs_coupons.customer_id=?1 " +
            " AND coupons.id=customers_vs_coupons.coupon_id",
            nativeQuery = true)
    List<Coupon> findCouponsByCustomerId(int customerId);

    @Query(value = "SELECT coupons.*" +
            " FROM coupons, customers_vs_coupons" +
            " WHERE coupons.category_id=:#{#category?.name()} " +
            " AND customers_vs_coupons.customer_id=:customerId" +
            " AND coupons.id=customers_vs_coupons.coupon_id",
            nativeQuery = true)
    List<Coupon> findCouponsByCategoryAndCustomerId(@Param("category") Category category, @Param("customerId") int customerId);

    @Query(value = "SELECT coupons.*" +
            " FROM coupons, customers_vs_coupons" +
            " WHERE customers_vs_coupons.customer_id=?1 " +
            " AND coupons.price<=?2" +
            " AND coupons.id=customers_vs_coupons.coupon_id",
            nativeQuery = true)
    List<Coupon> findCouponsByCustomerIdUpToPrice(int customerId, double price);

    @Transactional
    @Modifying
    @Query(value = "DELETE from customers_vs_coupons where coupon_id = ?1", nativeQuery = true)
    void deleteCouponPurchase(int couponId);

    @Transactional
    @Modifying
    @Query(value = "DELETE from customers_vs_coupons WHERE customer_id<>0 AND coupon_id IN" +
            " (select id from coupons where company_id=?1)", nativeQuery = true)
    void deleteCouponPurchaseByCompanyId(int companyId);

    @Transactional
    @Modifying
    @Query(value = "DELETE from customers_vs_coupons where customer_id = ?1", nativeQuery = true)
    void deleteCouponPurchaseByCustomerId(int customerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coupons WHERE id>0 AND end_date<=?1",
            nativeQuery = true)
    void deleteByEndDateBefore(Date date);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons" +
            " WHERE customer_id<>0" +
            " AND coupon_id IN (SELECT id" +
            " FROM coupons" +
            " WHERE end_date<=?1)",
            nativeQuery = true)
    void deleteExpiredCouponPurchase(Date date);

    @Query(value = "SELECT count(*) FROM customers_vs_coupons" +
            " WHERE customer_id=?1 AND coupon_id=?2",
            nativeQuery = true)
    int isCouponPurchased(int customerId, int couponId);
}
