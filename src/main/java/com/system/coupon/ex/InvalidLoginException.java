package com.system.coupon.ex;

public class InvalidLoginException extends AbstractAuthenticationException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
