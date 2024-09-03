package com.project.uber.uberapp.strategies;

import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<DriverEntity> findMatchingDrivers(RideRequest rideRequest);

}
