package com.system.coupon.rest.ex;

public class UnknownEmailOrPasswordException extends Exception {
    public UnknownEmailOrPasswordException(String msg) {
        super(msg);
    }
}
