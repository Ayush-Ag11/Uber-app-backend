package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.RiderDTO;
import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Rating;
import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.RiderEntity;
import com.project.uber.uberapp.exceptions.ResourceNotFoundException;
import com.project.uber.uberapp.exceptions.RuntimeConflictExceptions;
import com.project.uber.uberapp.repositories.DriverRepository;
import com.project.uber.uberapp.repositories.RatingRepository;
import com.project.uber.uberapp.repositories.RiderRepository;
import com.project.uber.uberapp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDTO rateDriver(Ride ride, Integer rating) {
        DriverEntity driver = ride.getDriver();
        Rating ratingObject = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id : " + ride.getId()));

        if (ratingObject.getDriverRating() != null)
            throw new RuntimeConflictExceptions("Driver has already been rated, cannot rate again");

        ratingObject.setDriverRating(rating);

        ratingRepository.save(ratingObject);

        double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);
        driver.setRating(newRating);

        DriverEntity savedDriver = driverRepository.save(driver);

        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public RiderDTO rateRider(Ride ride, Integer rating) {
        RiderEntity rider = ride.getRider();
        Rating ratingObject = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id : " + ride.getId()));

        if (ratingObject.getRiderRating() != null)
            throw new RuntimeConflictExceptions("Rider has already been rated, cannot rate again");

        ratingObject.setRiderRating(rating);

        ratingRepository.save(ratingObject);

        double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average()
                .orElse(0.0);
        rider.setRating(newRating);

        RiderEntity savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider, RiderDTO.class);

    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();

        ratingRepository.save(rating);
    }
}
