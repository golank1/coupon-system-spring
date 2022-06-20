package com.jb.coupon_system_spring.advice;

import com.jb.coupon_system_spring.exceptions.CompanyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CompanyAdvice {
    /**
     * This method check the exception for the company's
     * @param e the exception
     * @return error detail for a company
     */
    @ExceptionHandler(value = {CompanyException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleCompanyException(Exception e){
        return new ErrorDetail("Company Error",e.getMessage());
    }
}
