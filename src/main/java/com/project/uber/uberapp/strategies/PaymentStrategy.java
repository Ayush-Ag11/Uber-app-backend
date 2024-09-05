package com.project.uber.uberapp.strategies;

import com.project.uber.uberapp.entities.Payment;

public interface PaymentStrategy {

    Double PLATFORM_COMMISSION = 0.3;

    void processPayment(Payment payment);

}
