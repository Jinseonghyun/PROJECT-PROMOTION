package com.backend.pointservicebatch.repository;

import com.backend.pointservicebatch.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
