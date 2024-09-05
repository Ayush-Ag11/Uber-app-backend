package com.project.uber.uberapp.dto;

import com.project.uber.uberapp.entities.enums.TransactionMethod;
import com.project.uber.uberapp.entities.enums.TransactionType;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransactionsDTO {

    private Long id;

    private double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    @OneToOne
    private RideDTO ride;

    private String transactionId;

    private LocalDateTime timeStamp;

    private WalletDTO wallet;

}
