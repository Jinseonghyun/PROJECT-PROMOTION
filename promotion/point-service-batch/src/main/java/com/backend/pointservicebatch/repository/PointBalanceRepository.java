package com.backend.pointservicebatch.repository;

import com.backend.pointservicebatch.domain.PointBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointBalanceRepository extends JpaRepository<PointBalance, Long> {
}
