package com.swetha.payment_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swetha.payment_system.model.Payment;
import com.swetha.payment_system.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public String create(@RequestBody Payment p) throws Exception {
        service.createPayment(p);
        return "Payment Added";
    }

    @GetMapping
    public List<Payment> getAll() {
        return service.getAllPayments();
    }
}