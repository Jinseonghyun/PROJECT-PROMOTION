package com.backend.timesaleservice.repository;

import com.backend.timesaleservice.domain.TimeSaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSaleOrderRepository extends JpaRepository<TimeSaleOrder, Long> {
}
