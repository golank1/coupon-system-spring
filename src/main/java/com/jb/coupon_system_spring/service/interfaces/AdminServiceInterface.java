package com.jb.coupon_system_spring.service.interfaces;

import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.exceptions.AdminException;
import com.jb.coupon_system_spring.exceptions.CompanyException;
import com.jb.coupon_system_spring.exceptions.CustomerException;

import java.util.List;

public interface AdminServiceInterface {
    int addCompany(Company company) throws CompanyException;
    void updateCompany(Company company) throws AdminException;
    void deleteCompany(int companyId) throws AdminException;
    List<Company> getAllCompanies();
    Company getCompanyById(int companyId) throws AdminException;
    int addCustomer(Customer customer) throws CustomerException;
    void updateCustomer(Customer customer) throws AdminException;
    void deleteCustomer(int customerId) throws AdminException;
    List<Customer> getAllCustomers();
    Customer getCustomerById(int customerId) throws AdminException;
}
