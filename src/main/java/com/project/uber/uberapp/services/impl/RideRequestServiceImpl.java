package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.entities.RideRequest;
import com.project.uber.uberapp.exceptions.ResourceNotFoundException;
import com.project.uber.uberapp.repositories.RideRequestRepository;
import com.project.uber.uberapp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride request not found with id " + rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ride request not found with id " + rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
