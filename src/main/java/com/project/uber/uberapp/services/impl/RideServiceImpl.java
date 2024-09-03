package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.dto.RideRequestDTO;
import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.RideRequest;
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

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        return null;
    }

    @Override
    public void matchWithDriver(RideRequestDTO rideRequestDTO) {

    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, DriverEntity driverEntity) {

        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        Ride ride = modelMapper.map(rideRequest,Ride.class);

        ride.setRideRequestStatus(RideStatus.CONFIRMED);
        ride.setDriver(driverEntity);
        ride.setOtp(generateRandomOtp());
        ride.setRider(null);

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Long rideId, RideStatus rideStatus) {
        return null;
    }

    @Override
    public List<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest) {
        return List.of();
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Long driverId, PageRequest pageRequest) {
        return null;
    }

    private String generateRandomOtp(){
        Random random = new Random();
        return String.valueOf(random.nextInt(9999));
    }
}
