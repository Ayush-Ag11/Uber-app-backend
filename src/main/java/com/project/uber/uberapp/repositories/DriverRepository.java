package com.project.uber.uberapp.repositories;

import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.UserEntity;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickupLocation) AS distance " +
            "FROM driver_entity d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10", nativeQuery = true)
    List<DriverEntity> findTenNearestDriver(Point pickupLocation);

    @Query(value = "SELECT d.* " +
            "FROM driver_entity d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 15000) " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10", nativeQuery = true)
    List<DriverEntity> findTenNearByTopRatedDriver(Point pickupLocation);

    Optional<DriverEntity> findByUser(UserEntity user);
}

