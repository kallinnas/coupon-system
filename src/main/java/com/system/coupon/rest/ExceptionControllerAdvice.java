package com.system.coupon.rest;

import com.system.coupon.ex.CouponNotInStockException;
import com.system.coupon.ex.UserAlreadyExistException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(UserAlreadyExistException ex) {
        return response(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(CouponNotInStockException ex) {
        return response(ex.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorResponse> response(String message, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(message, LocalDateTime.now(), status.getReasonPhrase(), status.value());
        return new ResponseEntity<>(error, status);
    }

    @Getter
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    @RequiredArgsConstructor // Generates constructor for every final or @NonNull field
    private static class ErrorResponse {
        String message;
        LocalDateTime timestamp;
        String statusText;
        int statusCode;
    }

}
