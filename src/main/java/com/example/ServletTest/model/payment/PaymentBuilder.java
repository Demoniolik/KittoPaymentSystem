package com.example.ServletTest.model.payment;

import java.time.LocalDateTime;

public class PaymentBuilder {
    private long id;
    private double money;
    private String description;
    private PaymentStatus paymentStatus;
    private LocalDateTime date;
    private long creditCardIdSource;
    private long creditCardIdDestination;
    private PaymentCategory paymentCategory;

    public PaymentBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public PaymentBuilder setMoney(double money) {
        this.money = money;
        return this;
    }

    public PaymentBuilder setDescription(String description) {
        this.description = description;
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

    public PaymentBuilder setCreditCardIdDestination(long creditCardIdDestination) {
        this.creditCardIdDestination = creditCardIdDestination;
        return this;
    }

    public PaymentBuilder setPaymentCategory(PaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
        return this;
    }

    public Payment build() {
        return new Payment(id, money, description, paymentStatus, date, creditCardIdSource,
                creditCardIdDestination, paymentCategory);
    }

}
