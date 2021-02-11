package com.example.ServletTest.model.payment;

import java.time.LocalDateTime;

public class Payment {
    private long id;
    private double money;
    private String description;
    private PaymentStatus paymentStatus;
    private LocalDateTime date;
    private long creditCardIdSource;
    private long creditCardIdDestination;
    private PaymentCategory paymentCategory;

    public Payment() {}

    public Payment(long id, double money, String description, PaymentStatus paymentStatus,
                   LocalDateTime date, long creditCardIdSource,
                   long creditCardIdDestination, PaymentCategory paymentCategory) {
        this.id = id;
        this.money = money;
        this.description = description;
        this.paymentStatus = paymentStatus;
        this.date = date;
        this.creditCardIdSource = creditCardIdSource;
        this.creditCardIdDestination = creditCardIdDestination;
        this.paymentCategory = paymentCategory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getCreditCardIdSource() {
        return creditCardIdSource;
    }

    public void setCreditCardIdSource(long creditCardIdSource) {
        this.creditCardIdSource = creditCardIdSource;
    }

    public long getCreditCardIdDestination() {
        return creditCardIdDestination;
    }

    public void setCreditCardIdDestination(long creditCardIdDestination) {
        this.creditCardIdDestination = creditCardIdDestination;
    }

    public PaymentCategory getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(PaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
    }
}
