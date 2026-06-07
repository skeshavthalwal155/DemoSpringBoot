package com.example.store;

import org.springframework.context.annotation.Lazy;

// @Service
@Lazy
public class OrderService {

    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
        System.out.println("OrderService Created");
    }

    public void placeOrder() {
        paymentService.processPayment(1000);
    }
}
