package com.project.uber.uberapp.strategies.impl;

import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.RideRequest;
import com.project.uber.uberapp.repositories.DriverRepository;
import com.project.uber.uberapp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<DriverEntity> findMatchingDrivers(RideRequest rideRequest) {
        return driverRepository.findTenNearByTopRatedDriver(rideRequest.getPickupLocation());
    }
}
