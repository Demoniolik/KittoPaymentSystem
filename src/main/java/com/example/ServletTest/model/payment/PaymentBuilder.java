package com.example.ServletTest.model.payment;

import java.time.LocalDateTime;

public class PaymentBuilder {
    private long id;
    private double money;
    private PaymentStatus paymentStatus;
    private LocalDateTime date;
    private long creditCardIdSource;
    private long creditCardUserIdSource;
    private long creditCardIdDestination;
    private long creditCardUserIdDestination;

    public PaymentBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public PaymentBuilder setMoney(double money) {
        this.money = money;
        return this;
    }

    public PaymentBuilder setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public PaymentBuilder setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public PaymentBuilder setCreditCardIdSource(long creditCardIdSource) {
        this.creditCardIdSource = creditCardIdSource;
        return this;
    }

    public PaymentBuilder setCreditCardUserIdSource(long creditCardUserIdSource) {
        this.creditCardUserIdSource = creditCardUserIdSource;
        return this;
    }

    public PaymentBuilder setCreditCardIdDestination(long creditCardIdDestination) {
        this.creditCardIdDestination = creditCardIdDestination;
        return this;
    }

    public PaymentBuilder setCreditCardUserIdDestination(long creditCardUserIdDestination) {
        this.creditCardUserIdDestination = creditCardUserIdDestination;
        return this;
    }

    public Payment build() {
        return new Payment(id, money, paymentStatus, date, creditCardIdSource, creditCardUserIdSource,
                creditCardIdDestination, creditCardUserIdDestination);
    }

}
