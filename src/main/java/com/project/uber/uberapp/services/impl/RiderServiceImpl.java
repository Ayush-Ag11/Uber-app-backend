package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.RideDTO;
import com.project.uber.uberapp.dto.RideRequestDTO;
import com.project.uber.uberapp.dto.RiderDTO;
import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.RideRequest;
import com.project.uber.uberapp.entities.RiderEntity;
import com.project.uber.uberapp.entities.UserEntity;
import com.project.uber.uberapp.entities.enums.RideRequestStatus;
import com.project.uber.uberapp.exceptions.ResourceNotFoundException;
import com.project.uber.uberapp.repositories.RideRequestRepository;
import com.project.uber.uberapp.repositories.RiderRepository;
import com.project.uber.uberapp.services.RiderService;
import com.project.uber.uberapp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    public RiderDTO cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDTO getMyProfile() {
        return null;
    }

    @Override
    public List<RideDTO> getAllMyRides() {
        return List.of();
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
