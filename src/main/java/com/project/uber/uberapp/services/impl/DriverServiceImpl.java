package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.RideDTO;
import com.project.uber.uberapp.dto.RiderDTO;
import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.RideRequest;
import com.project.uber.uberapp.entities.enums.RideRequestStatus;
import com.project.uber.uberapp.repositories.DriverRepository;
import com.project.uber.uberapp.services.DriverService;
import com.project.uber.uberapp.services.RideRequestService;
import com.project.uber.uberapp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public RideDTO acceptRide(Long rideRequestId) {

        RideRequest rideRequest = rideRequestService.findRequestById(rideRequestId);

        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException("Ride request is already accepted, status is :" + rideRequest.getRideRequestStatus());
        }

        DriverEntity driver = getCurrentDriver();
        if (Boolean.FALSE.equals(driver.getAvailable())) {
            throw new RuntimeException("Driver can not accept ride due to unavailability");
        }

        driver.setAvailable(Boolean.FALSE);
        driverRepository.save(driver);

        Ride ride = rideService.createNewRide(rideRequest, driver);
        return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDTO startRide(Long rideId, String otp) {
        return null;
    }

    @Override
    public RideDTO endRide(Long rideId) {
        return null;
    }

    @Override
    public RiderDTO rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDTO getMyProfile() {
        return null;
    }

    @Override
    public List<RideDTO> getAllMyRides() {
        return List.of();
    }

    @Override
    public DriverEntity getCurrentDriver() {
        return driverRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Driver not found with id 2"));
    }
}
