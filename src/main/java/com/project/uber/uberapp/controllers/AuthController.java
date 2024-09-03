package com.project.uber.uberapp.controllers;

import com.project.uber.uberapp.dto.SignupDTO;
import com.project.uber.uberapp.dto.UserDTO;
import com.project.uber.uberapp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    UserDTO signUp(@RequestBody SignupDTO signupDto) {
        return authService.signup(signupDto);
    }

}
