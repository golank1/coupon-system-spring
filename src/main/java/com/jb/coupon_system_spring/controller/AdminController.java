package com.jb.coupon_system_spring.controller;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.beans.ErrorTypes;
import com.jb.coupon_system_spring.exceptions.AdminException;
import com.jb.coupon_system_spring.exceptions.CompanyException;
import com.jb.coupon_system_spring.exceptions.CustomerException;
import com.jb.coupon_system_spring.exceptions.LoginException;
import com.jb.coupon_system_spring.service.AdminService;
import com.jb.coupon_system_spring.util.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class AdminController {
    private final JWT jwt;
    private final AdminService adminService;
    private final ClientType clientType=ClientType.ADMIN;

    /**
     * This method returns all companies in the database.
     * @param token is an authorization token.
     * @return a ResponseEntity with a token in the header and a list of all companies in the body.
     * @throws LoginException if the user is unauthorised.
     */
    @GetMapping("/allCompanies")
    public ResponseEntity<?> getAllCompanies
            (@RequestHeader(name = "Authorization") String token)
            throws LoginException {
        jwt.checkClient(adminService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .body(adminService.getAllCompanies());
    }

    /**
     * This method returns all customers from the database.
     * @param token is an authorization token.
     * @return a ResponseEntity with a token in the header and a list of al customers in the body.
     * @throws LoginException if the user is unauthorized.
     */
    @GetMapping("/allCustomers")
    public ResponseEntity<?> getAllCustomers
            (@RequestHeader(name = "Authorization") String token)
            throws LoginException {
        jwt.checkClient(adminService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .body(adminService.getAllCustomers());
    }

    /**
     * THis method returns a single company from the database based on id.
     * @param token is an authorization token.
     * @param id is the id of the company we want to receive.
     * @return a ResponseEntity with a token in the header and a company in the body.
     * @throws AdminException if the company doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @GetMapping("/company/{id}")
    public ResponseEntity<?> getCompanyById
            (@RequestHeader(name = "Authorization") String token, @PathVariable int id)
            throws AdminException, LoginException {
        jwt.checkClient(adminService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .body(adminService.getCompanyById(id));
    }

    /**
     * This method returns a single customer from the database based on id.
     * @param token is an authorization token.
     * @param id is the id of the customer we want to receive.
     * @return a ResponseEntity with a token in the header and a customer in the body.
     * @throws AdminException if the customer doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerById
            (@RequestHeader(name = "Authorization") String token, @PathVariable int id)
            throws AdminException, LoginException {
        jwt.checkClient(adminService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .body(adminService.getCustomerById(id));

    }

    /**
     * This method adds new company to the database.
     * @param token is an authorization token.
     * @param company is a new company we want to add.
     * @return a ResponseEntity with a token in the header.
     * @throws LoginException if the user is unauthorized.
     */
    @PostMapping("/company/add")
    public ResponseEntity<?> addCompany
            (@RequestHeader(name = "Authorization") String token, @RequestBody Company company)
            throws LoginException, CompanyException {
        jwt.checkClient(adminService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .body(adminService.addCompany(company));
    }

    /**
     * This method adds new customer to the database.
     * @param token is an authorization token.
     * @param customer is a new customer we want to add.
     * @return a ResponseEntity with a token in the header.
     * @throws LoginException if the user is unauthorized.
     */
    @PostMapping("/customer/add")
    public ResponseEntity<?> addCustomer
            (@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer)
            throws LoginException, CustomerException {
        jwt.checkClient(adminService,token,clientType);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .body(adminService.addCustomer(customer));
    }

    /**
     * This method updates an existing company on the database.
     * @param token is an authorization token.
     * @param company is the company we want to update.
     * @return a ResponseEntity with a token in the header.
     * @throws AdminException if the company doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @PutMapping("/company/update")
    public ResponseEntity<?> updateCompany
            (@RequestHeader(name = "Authorization") String token, @RequestBody Company company)
            throws AdminException, LoginException {
        jwt.checkClient(adminService,token,clientType);
        adminService.updateCompany(company);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .build();

    }

    /**
     * This method updates an existing customer in the database.
     * @param token is an authorization token.
     * @param customer is the customer we want to update.
     * @return a ResponseEntity with a token in the header.
     * @throws AdminException if the customer doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @PutMapping("/customer/update")
    public ResponseEntity<?> updateCustomer
            (@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer)
            throws AdminException, LoginException {
        jwt.checkClient(adminService,token,clientType);
        adminService.updateCustomer(customer);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .build();
    }

    /**
     * This method deletes an existing company from the database based on id.
     * @param token is an authorization token.
     * @param id is the id of the company we want to delete.
     * @return a ResponseEntity with a token int the header.
     * @throws AdminException if the company doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @DeleteMapping("/company/delete/{id}")
    public ResponseEntity<?> deleteCompany
            (@RequestHeader(name = "Authorization") String token, @PathVariable int id)
            throws AdminException, LoginException {
        jwt.checkClient(adminService,token,clientType);
        adminService.deleteCompany(id);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .build();
    }

    /**
     * This method deletes an existing customer from the database based on id.
     * @param token is an authorization token.
     * @param id is the id of the customer we want to delete.
     * @return a ResponseEntity with a token in the header.
     * @throws AdminException if the customer doesn't exist.
     * @throws LoginException if the user is unauthorized.
     */
    @DeleteMapping("/customer/delete/{id}")
    public ResponseEntity<?> deleteCustomer
            (@RequestHeader(name = "Authorization") String token, @PathVariable int id)
            throws AdminException, LoginException {
        jwt.checkClient(adminService,token,clientType);
        adminService.deleteCustomer(id);
        return ResponseEntity.ok()
                .header("Authorization", adminService.getToken())
                .build();
    }

}
