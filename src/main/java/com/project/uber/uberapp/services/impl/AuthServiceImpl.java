package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.SignupDTO;
import com.project.uber.uberapp.dto.UserDTO;
import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.UserEntity;
import com.project.uber.uberapp.entities.enums.Role;
import com.project.uber.uberapp.exceptions.ResourceNotFoundException;
import com.project.uber.uberapp.exceptions.RuntimeConflictExceptions;
import com.project.uber.uberapp.repositories.UserRepository;
import com.project.uber.uberapp.security.JWTService;
import com.project.uber.uberapp.services.AuthService;
import com.project.uber.uberapp.services.DriverService;
import com.project.uber.uberapp.services.RiderService;
import com.project.uber.uberapp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken,refreshToken};
    }

    @Override
    @Transactional
    public UserDTO signup(SignupDTO signupDTO) {
        UserEntity user = userRepository.findByEmail(signupDTO.getEmail()).orElse(null);
        if (user != null)
            throw new RuntimeConflictExceptions("Cannot signup, User already exists with email " + signupDTO.getEmail());
        UserEntity newUser = modelMapper.map(signupDTO, UserEntity.class);
        newUser.setRoles(Set.of(Role.RIDER));
        newUser.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        UserEntity savedUser = userRepository.save(newUser);

        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public DriverDTO onBoardNewDriver(Long userId, String vehicleId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));

        if (user.getRoles().contains(Role.DRIVER)) {
            throw new RuntimeConflictExceptions("user with id : " + userId + " is already a driver");
        }

        DriverEntity createDriver = DriverEntity.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        DriverEntity driver = driverService.createNewDriver(createDriver);

        return modelMapper.map(driver, DriverDTO.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));

        return jwtService.generateAccessToken(user);
    }
}
