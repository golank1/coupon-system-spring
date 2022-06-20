package com.jb.coupon_system_spring.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetail {
    private String error;
    private String description;
}
