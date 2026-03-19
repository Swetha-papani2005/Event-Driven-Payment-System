package com.swetha.payment_system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.swetha.payment_system.model.Payment;

@Service
public class PaymentService {

    private final List<Payment> payments = new ArrayList<>();

    public void createPayment(Payment p) {
        p.setStatus("SUCCESS");
        payments.add(p);
        System.out.println("Processed Payment: " + p.getId());
    }

    public List<Payment> getAllPayments() {
        return payments;
    }
}