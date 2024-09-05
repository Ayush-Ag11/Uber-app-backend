package com.project.uber.uberapp.services;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.RideDTO;
import com.project.uber.uberapp.dto.RideRequestDTO;
import com.project.uber.uberapp.dto.RiderDTO;
import com.project.uber.uberapp.entities.RiderEntity;
import com.project.uber.uberapp.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideRequestDTO requestRide(RideRequestDTO rideRequestDTO);

    RideDTO cancelRide(Long rideId);

    DriverDTO rateDriver(Long rideId, Integer rating);

    RiderDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    RiderEntity createNewRider(UserEntity user);

    RiderEntity getCurrentRider();

}
