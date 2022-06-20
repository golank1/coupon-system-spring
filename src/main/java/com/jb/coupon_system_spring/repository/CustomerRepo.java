package com.jb.coupon_system_spring.repository;

import com.jb.coupon_system_spring.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

    Optional<Customer> findByEmail(String email);
}
