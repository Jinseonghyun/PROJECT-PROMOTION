package com.backend.timesaleservice.service.v1;

import com.backend.timesaleservice.aop.TimeSaleMetered;
import com.backend.timesaleservice.domain.Product;
import com.backend.timesaleservice.domain.TimeSale;
import com.backend.timesaleservice.domain.TimeSaleOrder;
import com.backend.timesaleservice.domain.TimeSaleStatus;
import com.backend.timesaleservice.dto.TimeSaleDto;
import com.backend.timesaleservice.repository.ProductRepository;
import com.backend.timesaleservice.repository.TimeSaleOrderRepository;
import com.backend.timesaleservice.repository.TimeSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TimeSaleService {
    private final TimeSaleRepository timeSaleRepository;
    private final ProductRepository productRepository;
    private final TimeSaleOrderRepository timeSaleOrderRepository;

    @Transactional
    public TimeSale createTimeSale(TimeSaleDto.CreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        validateTimeSale(request.getQuantity(), request.getDiscountPrice(),
                request.getStartAt(), request.getEndAt());

        TimeSale timeSale = TimeSale.builder()
                .product(product)
                .quantity(request.getQuantity())
                .remainingQuantity(request.getQuantity())
                .discountPrice(request.getDiscountPrice())
                .startAt(request.getStartAt())
                .endAt(request.getEndAt())
                .status(TimeSaleStatus.ACTIVE)
                .build();

        return timeSaleRepository.save(timeSale);
    }

    @Transactional(readOnly = true)
    public TimeSale getTimeSale(Long timeSaleId) {
        return timeSaleRepository.findById(timeSaleId)
                .orElseThrow(() -> new IllegalArgumentException("Time sale not found"));
    }

    @Transactional(readOnly = true)
    public Page<TimeSale> getOngoingTimeSales(Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        return timeSaleRepository.findAllByStartAtBeforeAndEndAtAfterAndStatus(now, TimeSaleStatus.ACTIVE, pageable);
    }

    @Transactional
    @TimeSaleMetered(version = "v1")
    public TimeSale purchaseTimeSale(Long timeSaleId, TimeSaleDto.PurchaseRequest request) {
        TimeSale timeSale = timeSaleRepository.findByIdWithPessimisticLock(timeSaleId)
                .orElseThrow(() -> new IllegalArgumentException("TimeSale not found"));

        timeSale.purchase(request.getQuantity());
        timeSaleRepository.save(timeSale);

        TimeSaleOrder order = TimeSaleOrder.builder()
                .userId(request.getUserId())
                .timeSale(timeSale)
                .quantity(request.getQuantity())
                .discountPrice(timeSale.getDiscountPrice())
                .build();

        TimeSaleOrder savedOrder = timeSaleOrderRepository.save(order);
        savedOrder.complete();

        return timeSale;
    }

    private void validateTimeSale(Long quantity, Long discountPrice, LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (discountPrice <= 0) {
            throw new IllegalArgumentException("Discount price must be positive");
        }
    }
}
