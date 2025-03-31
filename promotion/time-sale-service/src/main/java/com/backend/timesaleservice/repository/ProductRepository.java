package com.backend.timesaleservice.repository;

import com.backend.timesaleservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
