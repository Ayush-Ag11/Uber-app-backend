package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.dto.RideRequestDTO;
import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.RideRequest;
import com.project.uber.uberapp.entities.RiderEntity;
import com.project.uber.uberapp.entities.enums.RideRequestStatus;
import com.project.uber.uberapp.entities.enums.RideStatus;
import com.project.uber.uberapp.repositories.RideRepository;
import com.project.uber.uberapp.services.RideRequestService;
import com.project.uber.uberapp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found with id " + rideId));
    }

    @Override
    public void matchWithDriver(RideRequestDTO rideRequestDTO) {

    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, DriverEntity driverEntity) {

        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        Ride ride = modelMapper.map(rideRequest,Ride.class);

        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driverEntity);
        ride.setOtp(generateRandomOtp());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(RiderEntity rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider, pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(DriverEntity driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver, pageRequest);
    }

    private String generateRandomOtp(){
        Random random = new Random();
        return String.valueOf(random.nextInt(9999));
    }
}
