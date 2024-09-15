package com.project.uber.uberapp.repositories;

import com.project.uber.uberapp.entities.RiderEntity;
import com.project.uber.uberapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RiderRepository extends JpaRepository<RiderEntity, Long> {
    Optional<RiderEntity> findByUser(UserEntity user);
}

