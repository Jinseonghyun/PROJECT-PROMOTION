package com.backend.couponservice.dto.v1;

import com.backend.couponservice.domain.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CouponDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IssueRequest {
        private Long couponPolicyId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListRequest {
        private Coupon.Status status;
        private Integer page;
        private Integer size;
    }
}
