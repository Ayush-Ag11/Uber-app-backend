package com.project.uber.uberapp.services.impl;

import com.project.uber.uberapp.entities.WalletTransactions;
import com.project.uber.uberapp.repositories.WalletTransactionRepository;
import com.project.uber.uberapp.services.WalletTransactionsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionsService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransactions walletTransactions) {
        walletTransactionRepository.save(walletTransactions);

    }
}
