package com.example.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Value("${payment-gateway}")
    private String paymentGatway;

    @Bean
    public PaymentService stripe() {
        return new StripePaymentService();
    }

    @Bean
    @Scope("prototype")
    public PaymentService paypal() {
        return new PaypalPaymentService();
    }

    @Bean
    public OrderService orderService() {
        if (paymentGatway.equals("stripe"))
            return new OrderService(stripe());
        else
            return new OrderService(paypal());
    }
}
