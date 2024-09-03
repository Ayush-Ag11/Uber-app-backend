package com.project.uber.uberapp.services;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.RideDTO;
import com.project.uber.uberapp.dto.RiderDTO;
import com.project.uber.uberapp.entities.DriverEntity;

import java.util.List;

public interface DriverService {

    RideDTO acceptRide(Long rideRequestId);

    RideDTO cancelRide(Long rideId);

    RideDTO startRide(Long rideId, String otp);

    RideDTO endRide(Long rideId);

    RiderDTO rateRider(Long rideId, Integer rating);

    DriverDTO getMyProfile();

    List<RideDTO> getAllMyRides();

    DriverEntity getCurrentDriver();

}
