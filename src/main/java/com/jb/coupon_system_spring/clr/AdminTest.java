package com.jb.coupon_system_spring.clr;

import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.exceptions.AdminException;
import com.jb.coupon_system_spring.exceptions.CompanyException;
import com.jb.coupon_system_spring.exceptions.CustomerException;
import com.jb.coupon_system_spring.service.AdminService;
import com.jb.coupon_system_spring.util.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
public class AdminTest implements CommandLineRunner {
    private final AdminService adminService;
    @Override
    public void run(String... args) throws Exception {
        addCompanies();
        getSingleCompany();
        updateCompany();
        deleteCompany();
        getAllCompanies();
        addCustomers();
        getSingleCustomer();
        updateCustomer();
        deleteCustomer();
        getAllCustomers();
        getCompanyException();
        updateCompanyException();
        deleteCompanyException();
        getCustomerException();
        deleteCustomerException();




    }

    private void deleteCustomerException() {
        System.out.println("deleting customer that doesn't exist");
        try {
            adminService.deleteCustomer(100);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getCustomerException() {
        System.out.println("Get a customer that doesn't exist");
        try {
            adminService.getCustomerById(100);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteCompanyException() {
        System.out.println("Deleting company that doesn't exist");
        try {
            adminService.deleteCompany(100);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getCompanyException() {
        System.out.println("Get a company that doesn't exist");
        try {
            adminService.getCompanyById(100);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateCompanyException() {
        System.out.println("Change company name");
        try {
            Company company=adminService.getCompanyById(2);
            company.setName("exception");
            adminService.updateCompany(company);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }

    }

    private void deleteCustomer() {
        try {
            adminService.deleteCustomer(3);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteCompany() {
        try {
            adminService.deleteCompany(3);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateCustomer() {
        try {
            Customer customer=adminService.getCustomerById(2);
            customer.setFirstName("update");
            customer.setLastName("update");
            adminService.updateCustomer(customer);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateCompany() {
        try {
            Company company=adminService.getCompanyById(2);
            company.setEmail("update@company.com");
            adminService.updateCompany(company);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getAllCustomers() {
        List<Customer> customers=adminService.getAllCustomers();
        TablePrinter.print(customers);
    }

    private void getAllCompanies() {
        List<Company> companies=adminService.getAllCompanies();
        TablePrinter.print(companies);
    }

    private void getSingleCustomer() {
        try {
            Customer customer=adminService.getCustomerById(1);
            TablePrinter.print(customer);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getSingleCompany() {
        try {
            Company company=adminService.getCompanyById(1);
            TablePrinter.print(company);
        } catch (AdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addCustomers() {
        for (int counter = 0; counter < 3; counter++) {
            Customer customer= Customer
                    .builder()
                    .firstName("customer"+counter)
                    .lastName("customer"+counter)
                    .email("customer"+counter+"@test.com")
                    .password("customer")
                    .build();
            try {
                System.out.println( adminService.addCustomer(customer));
            } catch (CustomerException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private void addCompanies() {
        for (int counter = 0; counter < 3; counter++) {
            Company company= Company
                    .builder()
                    .name("company"+counter)
                    .email("company"+counter+"@test.com")
                    .password("company")
                    .build();
            try {
                System.out.println(adminService.addCompany(company));
            } catch (CompanyException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
