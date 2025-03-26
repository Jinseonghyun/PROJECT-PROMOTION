package com.backend.couponservice.exception;

public class CouponIssieException extends RuntimeException {
    public CouponIssieException(String message) {
        super(message);
    }

    public CouponIssieException(String message, Throwable cause) {
        super(message, cause);
    }
}
