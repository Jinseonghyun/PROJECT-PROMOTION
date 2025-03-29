package com.backend.pointservice.repository;

import com.backend.pointservice.domain.PointBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointBalanceRepository extends JpaRepository<PointBalance, Long> {
}
