package com.backend.couponservice.service.v1;

import com.backend.couponservice.config.UserIdInterceptor;
import com.backend.couponservice.domain.Coupon;
import com.backend.couponservice.domain.CouponPolicy;
import com.backend.couponservice.dto.v1.CouponDto;
import com.backend.couponservice.exception.CouponIssieException;
import com.backend.couponservice.repository.CouponPolicyRepository;
import com.backend.couponservice.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponPolicyService couponPolicyService;
    private final CouponPolicyRepository couponPolicyRepository;

    public Coupon issueCoupon(CouponDto.IssueRequest request, Long userId) {
        CouponPolicy couponPolicy = couponPolicyRepository.findByIdWithLock(request.getCouponPolicyId())
                .orElseThrow(() -> new CouponIssieException("쿠폰 정책을 찾을 수 없습니다."));

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(couponPolicy.getStartTime()) || now.isAfter(couponPolicy.getEndTime())) {
            throw new CouponIssieException("쿠폰 발급 기간이 아닙니다.");
        }

        long issuedCouponCount = couponRepository.countByCouponPolicyId(couponPolicy.getId());
        if (issuedCouponCount >= couponPolicy.getTotalQuantity()) {
            throw new CouponIssieException("쿠폰이 모두 소진되었습니다.");
        }

        Coupon coupon = Coupon.builder()
                .couponPolicy(couponPolicy)
                .userId(UserIdInterceptor.getCurrentUserId())
                .couponCode(generateCouponCode())
                .build();
        return couponRepository.save(coupon);
    }

    private String generateCouponCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
