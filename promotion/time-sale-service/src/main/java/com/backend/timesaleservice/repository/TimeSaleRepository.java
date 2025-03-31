package com.backend.timesaleservice.repository;

import com.backend.timesaleservice.domain.TimeSale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSaleRepository extends JpaRepository<TimeSale, Long> {
}
