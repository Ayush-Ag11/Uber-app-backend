package com.project.uber.uberapp.strategies.impl;

import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Payment;
import com.project.uber.uberapp.entities.enums.PaymentStatus;
import com.project.uber.uberapp.entities.enums.TransactionMethod;
import com.project.uber.uberapp.repositories.PaymentRepository;
import com.project.uber.uberapp.services.WalletService;
import com.project.uber.uberapp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        DriverEntity driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide() , TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
