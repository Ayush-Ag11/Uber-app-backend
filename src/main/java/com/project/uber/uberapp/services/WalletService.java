package com.project.uber.uberapp.services;

import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.UserEntity;
import com.project.uber.uberapp.entities.WalletEntity;
import com.project.uber.uberapp.entities.enums.TransactionMethod;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {

    WalletEntity addMoney(UserEntity user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    WalletEntity deductMoneyFromWallet(UserEntity user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    WalletEntity findWalletById(Long walletId);

    WalletEntity createNewWallet(UserEntity user);

    WalletEntity findByUser(UserEntity user);
}
