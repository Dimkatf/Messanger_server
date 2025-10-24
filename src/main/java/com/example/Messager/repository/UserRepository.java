package com.example.Messager.repository;

import com.example.Messager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);
    User findByPhone(String phone);
    User findByUserName(String userName);
}
