package com.project.uber.uberapp.controllers;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.OnBoardDriverDTO;
import com.project.uber.uberapp.dto.SignupDTO;
import com.project.uber.uberapp.dto.UserDTO;
import com.project.uber.uberapp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<UserDTO> signUp(@RequestBody SignupDTO signupDto) {
        return new ResponseEntity<>(authService.signup(signupDto), HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    UserDTO login(@RequestBody SignupDTO signupDto) {
//        return authService.login(signupDto);
//    }

    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDTO> onBoardNewDriver(@PathVariable Long userId,
                                               @RequestBody OnBoardDriverDTO onBoardDriverDTO) {
        return new ResponseEntity<>(authService.onBoardNewDriver(userId,
                onBoardDriverDTO.getVehicleId()), HttpStatus.CREATED);
    }

}
