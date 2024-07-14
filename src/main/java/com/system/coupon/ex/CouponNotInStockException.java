package com.system.coupon.ex;

public class CouponNotInStockException extends RuntimeException {
    public CouponNotInStockException(String message) {
        super(message);
    }
}
