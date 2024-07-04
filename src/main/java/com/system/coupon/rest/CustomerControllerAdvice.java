package com.system.coupon.rest;

import com.system.coupon.data.model.CustomerErrorResponse;
import com.system.coupon.rest.controller.CustomerController;
import com.system.coupon.rest.ex.InvalidLoginException;
import org.hibernate.type.AssociationType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = {AssociationType.class, CustomerController.class})
public class CustomerControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public CustomerErrorResponse handleUnauthorized(InvalidLoginException ex){
        return CustomerErrorResponse.of(HttpStatus.UNAUTHORIZED, "Unauthorised!");
    }
}
