package com.project.uber.uberapp.services;

import com.project.uber.uberapp.dto.RideRequestDTO;
import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.RideRequest;
import com.project.uber.uberapp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RideService {

    Ride getRideById(Long rideId);

    void matchWithDriver(RideRequestDTO rideRequestDTO);

    Ride createNewRide(RideRequest rideRequest, DriverEntity driverEntity);

    Ride updateRideStatus(Long rideId, RideStatus rideStatus);

    List<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Long driverId, PageRequest pageRequest);

}
