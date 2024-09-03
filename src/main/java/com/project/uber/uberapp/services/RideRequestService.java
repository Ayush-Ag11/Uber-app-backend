package com.project.uber.uberapp.services;

import com.project.uber.uberapp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}

