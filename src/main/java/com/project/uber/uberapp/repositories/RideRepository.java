package com.project.uber.uberapp.repositories;

import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.RiderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    Page<Ride> findByRider(RiderEntity rider, Pageable pageRequest);

    Page<Ride> findByDriver(DriverEntity driver, Pageable pageRequest);
}
