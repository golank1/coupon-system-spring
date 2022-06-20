package com.jb.coupon_system_spring.advice;

import com.jb.coupon_system_spring.exceptions.LoginException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class JWTAdvice {
    /**
     * This method check the exception for the token
     * @param e the exception
     * @return error detail for a token
     */
    @ExceptionHandler(value = {MalformedJwtException.class, ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail jwtError(Exception e){
        return new ErrorDetail("jwt error",e.getMessage());
    }
}
