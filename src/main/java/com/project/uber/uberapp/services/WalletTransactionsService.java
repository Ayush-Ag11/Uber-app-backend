package com.project.uber.uberapp.services;

import com.project.uber.uberapp.entities.WalletTransactions;
import org.springframework.stereotype.Service;

@Service
public interface WalletTransactionsService {

    void createNewWalletTransaction(WalletTransactions walletTransactions);

}
