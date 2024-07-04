package com.system.coupon.service.ex;

public class UserIsAlreadyExistException extends Exception {
    public UserIsAlreadyExistException(String msg) {
        super(msg);
    }
}
