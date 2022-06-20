package com.jb.coupon_system_spring.util;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.beans.ErrorTypes;
import com.jb.coupon_system_spring.exceptions.LoginException;
import com.jb.coupon_system_spring.service.ClientService;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWT {
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    //our secret key
    private String secretKey = "this+is+my+key+and+it+must+be+256+bit+long+fnjvnfjvvee";
    //decode the secret key
    private Key decodedSecretKey = new SecretKeySpec
            (Base64.getDecoder().decode(secretKey), this.signatureAlgorithm);
    public static final String CLIENT_TYPE = "userType";
    public static final String ID="id";

    /**
     * This method generates token based on given email.
     * Used for generating a token for ADMIN client type.
     * @param email is the email of the admin
     * @return an ADMIN jwt token.
     */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID,0);
        claims.put("name", "Admin");
        claims.put(CLIENT_TYPE, ClientType.ADMIN.getName());
        return "Bearer " + createToken(claims, email);
    }

    /**
     * This method generates token based on a  given Company.
     * Used for generating a token for COMPANY client type.
     * @param company is the company we generate a token for.
     * @return a COMPANY jwt token.
     */
    public String generateToken(Company company) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID, company.getId());
        claims.put("name", company.getName());
        claims.put(CLIENT_TYPE, ClientType.COMPANY.getName());
        return "Bearer " + createToken(claims, company.getEmail());
    }

    /**
     * This method generates token based on a  given Customer.
     * Used for generating a token for CUSTOMER client type.
     * @param customer is the customer we generate a token for.
     * @return a CUSTOMER jwt token.
     */
    public String generateToken(Customer customer) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID, customer.getId());
        claims.put("first name", customer.getFirstName());
        claims.put("last name", customer.getLastName());
        claims.put(CLIENT_TYPE, ClientType.CUSTOMER.getName());
        return "Bearer " + createToken(claims, customer.getEmail());
    }

    /**
     * This method checks if a given token is a valid token and generate new token if it is.
     * @param token is the jwt token we want to check.
     * @return a new jwt token based on the ClientType of the received token.
     * @throws MalformedJwtException if the received token is malformed.
     * @throws LoginException if the ClientType in the token doesn't match any of the existing client types.
     */
    public String checkUser(String token) throws MalformedJwtException, LoginException {
        Claims claims = extractAllClaims(token.replace("Bearer ", ""));
        String type = (String) claims.get(CLIENT_TYPE);
        if (type.equals(ClientType.ADMIN.getName())) {
            return generateToken(claims.getSubject());
        } else if (type.equals(ClientType.COMPANY.getName())) {
            Company company = new Company();
            company.setId((int) claims.get(ID));
            company.setName((String) claims.get("name"));
            company.setEmail(claims.getSubject());
            return generateToken(company);
        } else if (type.equals(ClientType.CUSTOMER.getName())) {
            Customer customer = new Customer();
            customer.setId((int) claims.get(ID));
            customer.setFirstName((String) claims.get("first name"));
            customer.setLastName((String) claims.get("last name"));
            customer.setEmail(claims.getSubject());
            return generateToken(customer);
        } else {
            throw new LoginException(ErrorTypes.UNAUTHORIZED_USER.getMessage());
        }
    }

    /**
     * This method extracts all claims from the jwt token.
     * @param token is a given token.
     * @return the Claims from the token.
     * @throws ExpiredJwtException if the token is expired.
     * @throws MalformedJwtException if the token is malformed.
     */
    private Claims extractAllClaims(String token) throws ExpiredJwtException, MalformedJwtException {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedSecretKey).build();
        return jwtParser.parseClaimsJws(token.replace("Bearer ", "")).getBody();
    }

    /**
     * This method gets the client type from the token.
     * @param token is the token we want to extract the client type from.
     * @return the client type inside the token.
     */
    public String getClientType(String token) {
        Claims claims = extractAllClaims(token.replace("Bearer ", ""));
        return (String) claims.get(CLIENT_TYPE);
    }

    /**
     * This method is used to get the id from the token.
     * The id corresponds to the id of the company/customer inside the database.
     * @param token is the token we want to extract the id from.
     * @return the id inside the token.
     */
    public int getId(String token){
        Claims claims = extractAllClaims(token.replace("Bearer ", ""));
        return (int) claims.get(ID);
    }

    /**
     * This method creates a new token.
     * @param claims contains the id and the ClientType of the token (and more details).
     * @param email is the subject of the token.
     * @return a new token
     */
    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(decodedSecretKey)
                .compact();
    }

    /**
     * This method checks if the ClientType inside the token matches the ClientType of the service.
     * This is for make sure that only authorised users can do actions on that specific service.
     * If it matches, the method sets the clientId of the service to be the clientId of the token
     * and sets the token of the service to new token.
     * @param service is one of the services of the system (adminService, companyService or customerService).
     * @param token is the token with specific ClientType and id.
     * @param type is the ClientTYpe of the service.
     * @throws LoginException if the ClientTYpe of the token doesn't match the ClientType of the service (unauthorised user).
     * @throws ExpiredJwtException if the token ids expired.
     * @throws MalformedJwtException if the token is malformed.
     */
    public void checkClient(ClientService service,String token, ClientType type)
            throws LoginException ,ExpiredJwtException, MalformedJwtException{
        if(getClientType(token).equals(type.getName())){
            service.setClientId(getId(token));
            service.setToken(checkUser(token));
        }else{
            throw new LoginException(ErrorTypes.UNAUTHORIZED_USER.getMessage());
        }
    }

//    public String generateGuestToken(){
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(ID,0);
//        claims.put("name", "Guest");
//        claims.put(CLIENT_TYPE, ClientType.GUEST.getName());
//        return "Bearer " + createToken(claims, "Guest");
//
//    }
}
