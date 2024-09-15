package com.project.uber.uberapp.services;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.SignupDTO;
import com.project.uber.uberapp.dto.UserDTO;

public interface AuthService {

    String[] login(String email, String password);

    UserDTO signup(SignupDTO signupDTO);

    DriverDTO onBoardNewDriver(Long userId, String vehicleId);

    String refreshToken(String refreshToken);
}
