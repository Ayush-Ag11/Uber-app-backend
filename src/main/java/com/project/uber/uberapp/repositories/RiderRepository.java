package com.project.uber.uberapp.repositories;

import com.project.uber.uberapp.entities.RiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RiderRepository extends JpaRepository<RiderEntity, Long> {
}
