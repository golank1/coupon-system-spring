package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.beans.ErrorTypes;
import com.jb.coupon_system_spring.exceptions.AdminException;
import com.jb.coupon_system_spring.exceptions.CompanyException;
import com.jb.coupon_system_spring.exceptions.CustomerException;
import com.jb.coupon_system_spring.service.interfaces.AdminServiceInterface;
import com.jb.coupon_system_spring.util.DataEnc;
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
public class AdminService extends ClientService implements AdminServiceInterface {
    /**
     * This method adds new company to the database.
     * @param company is the new company we want to add.
     */
    @Override
    public int addCompany(Company company) throws CompanyException {
        company.setPassword(DataEnc.setEncryptor(company.getPassword()));
        companyRepo.save(company);
        Optional<Company> companyOptional=companyRepo.findById(company.getId());
        if(companyOptional.isPresent()){
            return companyOptional.get().getId();
        }else{
            throw new CompanyException("something went wrong");
        }
    }

    /**
     * This method update an existing company in the database.
     * @param company is the company we want to update.
     * @throws AdminException if the company doesn't exist or when trying to update the company's name.
     */
    @Override
    public void updateCompany(Company company) throws AdminException {
        if (companyRepo.existsById(company.getId())) {
            if (!companyRepo.findById(company.getId()).get().getName().equals(company.getName())) {
                throw new AdminException(ErrorTypes.UNCHANGED_VALUE.getMessage());
            }
            if(!companyRepo.findById(company.getId()).get().getPassword().equals(company.getPassword())){
                company.setPassword(DataEnc.setEncryptor(company.getPassword()));
            }
            companyRepo.save(company);
        } else {
            throw new AdminException(ErrorTypes.COMPANY_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method deletes an existing company from the database based on its id.
     * @param companyId is the id of the company we want to delete.
     * @throws AdminException if the company doesn't exist.
     */
    @Override
    public void deleteCompany(int companyId) throws AdminException {
        //todo:make it better
        couponRepo.deleteCouponPurchaseByCompanyId(companyId);
        if (companyRepo.existsById(companyId)) {
            companyRepo.deleteById(companyId);
        }else {
            throw new AdminException(ErrorTypes.COMPANY_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method returns a list of all companies from the database.
     * @return a list of all companies from the database.
     */
    @Override
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    /**
     * This method get a single company from the database based on its id.
     * @param companyId is the id of the company we want to get.
     * @return the company from the database.
     * @throws AdminException if the company doesn't exist.
     */
    @Override
    public Company getCompanyById(int companyId) throws AdminException {
        Optional<Company> company = companyRepo.findById(companyId);
        if (company.isPresent()) {
            return company.get();
        } else {
            throw new AdminException(ErrorTypes.COMPANY_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method adds new customer to the database.
     *
     * @param customer is the new customer we want to add.
     * @return
     */
    @Override
    public int addCustomer(Customer customer) throws CustomerException {
        customer.setPassword(DataEnc.setEncryptor(customer.getPassword()));
        customerRepo.save(customer);
        Optional<Customer> customerOptional=customerRepo.findById(customer.getId());
        if(customerOptional.isPresent()){
            return customerOptional.get().getId();
        }else{
            throw new CustomerException("something went wrong");
        }
    }

    /**
     * This method updates an existing customer in the database.
     * @param customer is the customer we want to update.
     * @throws AdminException if the customer doesn't exist.
     */
    @Override
    public void updateCustomer(Customer customer) throws AdminException {
        if (customerRepo.existsById(customer.getId())) {
            if(!customerRepo.findById(customer.getId()).get().getPassword().equals(customer.getPassword())){
                customer.setPassword(DataEnc.setEncryptor(customer.getPassword()));
            }
            customerRepo.save(customer);
        } else {
            throw new AdminException(ErrorTypes.CUSTOMER_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method deletes an existing customer from the database based on its id.
     * @param customerId is the id of the customer we want to delete.
     * @throws AdminException if the customer doesn't exist.
     */
    @Override
    public void deleteCustomer(int customerId) throws AdminException {
        if (customerRepo.existsById(customerId)) {
            couponRepo.deleteCouponPurchaseByCustomerId(customerId);
            customerRepo.deleteById(customerId);
        } else {
            throw new AdminException(ErrorTypes.CUSTOMER_NOT_EXIST.getMessage());
        }
    }

    /**
     * This method returns a list of all customers from the database.
     * @return a list of all customers from the database.
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    /**
     * This method gets a single customer from the database based on its id.
     * @param customerId is the id of the customer we want to get.
     * @return the customer from the database.
     * @throws AdminException if the customer doesn't exist.
     */
    @Override
    public Customer getCustomerById(int customerId) throws AdminException {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new AdminException(ErrorTypes.CUSTOMER_NOT_EXIST.getMessage());
        }
    }
}
