package com.project.uber.uberapp.services;

import com.project.uber.uberapp.entities.Payment;
import com.project.uber.uberapp.entities.Ride;
import com.project.uber.uberapp.entities.enums.PaymentStatus;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);

}
