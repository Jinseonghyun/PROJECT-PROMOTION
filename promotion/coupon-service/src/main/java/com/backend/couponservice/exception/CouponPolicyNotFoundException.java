package com.backend.couponservice.exception;

public class CouponPolicyNotFoundException extends RuntimeException {
    public CouponPolicyNotFoundException(String message) {
        super(message);
    }
}
