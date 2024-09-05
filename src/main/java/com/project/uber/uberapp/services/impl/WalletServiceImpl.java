package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.UserEntity;
import com.project.uber.uberapp.entities.WalletEntity;
import com.project.uber.uberapp.entities.WalletTransactions;
import com.project.uber.uberapp.entities.enums.TransactionMethod;
import com.project.uber.uberapp.entities.enums.TransactionType;
import com.project.uber.uberapp.exceptions.ResourceNotFoundException;
import com.project.uber.uberapp.repositories.WalletRepository;
import com.project.uber.uberapp.services.WalletService;
import com.project.uber.uberapp.services.WalletTransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionsService walletTransactionsService;


    @Override
    @Transactional
    public WalletEntity addMoney(UserEntity user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        WalletEntity wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance() + amount);

        WalletTransactions walletTransactions = WalletTransactions.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionsService.createNewWalletTransaction(walletTransactions);

        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public WalletEntity deductMoneyFromWallet(UserEntity user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        WalletEntity wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance() - amount);

        WalletTransactions walletTransactions = WalletTransactions.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        wallet.getTransactions().add(walletTransactions);

        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public WalletEntity findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: " + walletId));
    }

    @Override
    public WalletEntity createNewWallet(UserEntity user) {
        WalletEntity wallet = new WalletEntity();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public WalletEntity findByUser(UserEntity user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user with id : " + user.getId()));
    }
}
