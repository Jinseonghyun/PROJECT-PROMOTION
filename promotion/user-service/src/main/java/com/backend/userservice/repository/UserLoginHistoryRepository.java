package com.backend.userservice.repository;

import com.backend.userservice.entity.User;
import com.backend.userservice.entity.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Integer> {
    List<UserLoginHistory> findByUserOrderByLoginTimeDesc(User user);
}
