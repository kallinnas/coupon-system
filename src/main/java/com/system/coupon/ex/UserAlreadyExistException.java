package com.system.coupon.ex;

public class UserAlreadyExistException extends AbstractAuthenticationException {
    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}
