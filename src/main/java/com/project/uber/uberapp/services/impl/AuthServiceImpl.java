package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.dto.DriverDTO;
import com.project.uber.uberapp.dto.SignupDTO;
import com.project.uber.uberapp.dto.UserDTO;
import com.project.uber.uberapp.entities.UserEntity;
import com.project.uber.uberapp.entities.enums.Role;
import com.project.uber.uberapp.exceptions.RuntimeConflictExceptions;
import com.project.uber.uberapp.repositories.UserRepository;
import com.project.uber.uberapp.services.AuthService;
import com.project.uber.uberapp.services.RiderService;
import com.project.uber.uberapp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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


    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    @Transactional
    public UserDTO signup(SignupDTO signupDTO) {
        UserEntity user = userRepository.findByEmail(signupDTO.getEmail()).orElse(null);
        if(user != null)
            throw new RuntimeConflictExceptions("Cannot signup, User already exists with email "+signupDTO.getEmail());
        UserEntity newUser = modelMapper.map(signupDTO, UserEntity.class);
        newUser.setRoles(Set.of(Role.RIDER));
        UserEntity savedUser = userRepository.save(newUser);

        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public DriverDTO onBoardNewDriver(Long userId) {
        return null;
    }
}
