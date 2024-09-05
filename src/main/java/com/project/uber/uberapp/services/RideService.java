package com.project.uber.uberapp.services;

import com.project.uber.uberapp.dto.RideRequestDTO;
import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.RideRequest;
import com.project.uber.uberapp.entities.RiderEntity;
import com.project.uber.uberapp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long rideId);

    void matchWithDriver(RideRequestDTO rideRequestDTO);

    Ride createNewRide(RideRequest rideRequest, DriverEntity driverEntity);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(RiderEntity rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(DriverEntity driver, PageRequest pageRequest);

}
