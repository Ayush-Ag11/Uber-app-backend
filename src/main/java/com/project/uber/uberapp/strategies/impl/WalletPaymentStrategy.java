package com.project.uber.uberapp.strategies.impl;

import com.project.uber.uberapp.entities.DriverEntity;
import com.project.uber.uberapp.entities.Payment;
import com.project.uber.uberapp.entities.RiderEntity;
import com.project.uber.uberapp.entities.enums.PaymentStatus;
import com.project.uber.uberapp.entities.enums.TransactionMethod;
import com.project.uber.uberapp.repositories.PaymentRepository;
import com.project.uber.uberapp.services.WalletService;
import com.project.uber.uberapp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        RiderEntity rider = payment.getRide().getRider();
        DriverEntity driver = payment.getRide().getDriver();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null,
                payment.getRide(), TransactionMethod.RIDE);

        double driverCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        walletService.addMoney(driver.getUser(), driverCut, null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
