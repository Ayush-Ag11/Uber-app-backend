package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.RideDTO;
import com.project.uber.uberapp.dto.RideRequestDTO;
import com.project.uber.uberapp.dto.RiderDTO;
import com.project.uber.uberapp.entities.*;
import com.project.uber.uberapp.entities.enums.RideRequestStatus;
import com.project.uber.uberapp.entities.enums.RideStatus;
import com.project.uber.uberapp.exceptions.ResourceNotFoundException;
import com.project.uber.uberapp.repositories.RideRequestRepository;
import com.project.uber.uberapp.repositories.RiderRepository;
import com.project.uber.uberapp.services.DriverService;
import com.project.uber.uberapp.services.RideService;
import com.project.uber.uberapp.services.RiderService;
import com.project.uber.uberapp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;


    @Override
    @Transactional
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {
        RiderEntity rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDTO, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);


        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<DriverEntity> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating())
                .findMatchingDrivers(rideRequest);

        // TODO :

        return modelMapper.map(savedRideRequest, RideRequestDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        RiderEntity rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if (!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider does not own this ride with id : "+ rideId);
        }

        if (ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride status is confirmed, hence can not be cancelled, status is :" + ride.getRideStatus());
        }

       Ride updatedRide = rideService.updateRideStatus(ride, RideStatus.CONFIRMED);
        driverService.updateDriverAvailability(ride.getDriver(),true);

        return modelMapper.map(updatedRide, RideDTO.class);

    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDTO getMyProfile() {
        RiderEntity rider = getCurrentRider();
        return modelMapper.map(rider, RiderDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        RiderEntity rider = getCurrentRider();
        return rideService.getAllRidesOfRider(rider, pageRequest)
                .map(ride -> modelMapper.map(ride, RideDTO.class));
    }

    @Override
    public RiderEntity createNewRider(UserEntity user) {
        RiderEntity rider = RiderEntity
                .builder()
                .userEntity(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public RiderEntity getCurrentRider() {
        return riderRepository.findById(1L).orElseThrow(() ->
                new ResourceNotFoundException("Rider not found with id " + 1));
    }
}
