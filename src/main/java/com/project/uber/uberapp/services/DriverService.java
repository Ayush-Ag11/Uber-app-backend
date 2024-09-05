package com.project.uber.uberapp.services;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.RideDTO;
import com.project.uber.uberapp.dto.RiderDTO;
import com.project.uber.uberapp.entities.DriverEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    RideDTO acceptRide(Long rideRequestId);

    RideDTO cancelRide(Long rideId);

    RideDTO startRide(Long rideId, String otp);

    RideDTO endRide(Long rideId);

    RiderDTO rateRider(Long rideId, Integer rating);

    DriverDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    DriverEntity getCurrentDriver();

    DriverEntity updateDriverAvailability(DriverEntity driver, Boolean available);

}
