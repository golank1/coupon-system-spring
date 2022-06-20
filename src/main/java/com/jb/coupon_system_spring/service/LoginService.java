package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.beans.ErrorTypes;
import com.jb.coupon_system_spring.exceptions.LoginException;
import com.jb.coupon_system_spring.repository.CompanyRepo;
import com.jb.coupon_system_spring.repository.CustomerRepo;
import com.jb.coupon_system_spring.util.DataEnc;
import com.jb.coupon_system_spring.util.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final CompanyRepo companyRepo;
    private final CustomerRepo customerRepo;
    private final JWT jwt;
    public static final String ADMIN_EMAIL="admin@admin.com";
    public static final String ADMIN_PASSWORD="admin";

    /**
     * This method generates new JWT token when a user logs in t the system.
     * @param email is the email of the user.
     * @param password is the password of the user.
     * @param clientType is the ClientType of the user.
     * @return new JWT token with the email, ClientType and id of the user.
     * @throws LoginException if email or password are incorrect
     * or if the user is unauthorised (exp. Company wants to log in as a customer).
     */
    public String login(String email, String password, ClientType clientType) throws LoginException {
        switch (clientType) {
            case ADMIN:
                if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                    return jwt.generateToken(email);
                } else {
                    throw new LoginException(ErrorTypes.BAD_LOGIN.getMessage());
                }
            case COMPANY:
                Optional<Company> company = companyRepo.findByEmail(email);
                if (company.isPresent() && DataEnc.getEncryptor(company.get().getPassword()).equals(password)) {
                    return jwt.generateToken(company.get());
                } else {
                    throw new LoginException(ErrorTypes.BAD_LOGIN.getMessage());
                }
            case CUSTOMER:
                Optional<Customer> customer = customerRepo.findByEmail(email);
                if (customer.isPresent() && DataEnc.getEncryptor(customer.get().getPassword()).equals(password)) {
                    return jwt.generateToken(customer.get());
                } else {
                    throw new LoginException(ErrorTypes.BAD_LOGIN.getMessage());
                }
            default:
                throw new LoginException(ErrorTypes.UNAUTHORIZED_USER.getMessage());
        }
    }

}
