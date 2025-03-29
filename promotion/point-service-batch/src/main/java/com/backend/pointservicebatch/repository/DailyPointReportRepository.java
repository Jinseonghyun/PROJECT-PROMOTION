package com.backend.pointservicebatch.repository;

import com.backend.pointservicebatch.domain.DailyPointReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPointReportRepository extends JpaRepository<DailyPointReport, Long> {
}
