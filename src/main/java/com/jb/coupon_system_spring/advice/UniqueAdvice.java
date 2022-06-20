package com.jb.coupon_system_spring.advice;

import com.jb.coupon_system_spring.beans.ErrorTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@ControllerAdvice
public class UniqueAdvice {
    /**
     * This method check the exception for the unique exception
     * @param e the exception
     * @return error detail for a unique exception
     */
    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ErrorDetail uniqueError(Exception e){
        return new ErrorDetail("invalid value", ErrorTypes.UNIQUE_FIELD.getMessage());
    }
}
