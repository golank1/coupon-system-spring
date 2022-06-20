package com.jb.coupon_system_spring.repository;

import com.jb.coupon_system_spring.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company,Integer> {

    Optional<Company> findByEmail(String email);
}
