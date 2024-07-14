package com.system.coupon.ex;

public abstract class AbstractAuthenticationException extends RuntimeException {
    public AbstractAuthenticationException(String message) {
        super(message);
    }
}
