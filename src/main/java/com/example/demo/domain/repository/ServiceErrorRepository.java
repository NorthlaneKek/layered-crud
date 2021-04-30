package com.example.demo.domain.repository;

import com.example.demo.domain.ServiceError;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceErrorRepository extends JpaRepository<ServiceError, Long> {

    Optional<ServiceError> findFirstByOrderByIdDesc();
}
