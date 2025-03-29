package com.backend.pointservice.repository;

import com.backend.pointservice.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
