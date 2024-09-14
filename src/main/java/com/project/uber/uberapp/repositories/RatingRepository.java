package com.project.uber.uberapp.repositories;

import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Rating;
import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.RiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findByRider(RiderEntity rider);

    List<Rating> findByDriver(DriverEntity driver);

    Optional<Rating> findByRide(Ride ride);
}
