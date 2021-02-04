package com.example.ServletTest.model.payment;

import java.time.LocalDateTime;

public class Payment {
    private long id;
    private double money;
    private PaymentStatus paymentStatus;
    private LocalDateTime date;
    private long creditCardIdSource;
    private long creditCardUserIdSource;
    private long creditCardIdDestination;
    private long creditCardUserIdDestination;

    public Payment() {}

    public Payment(long id, double money, PaymentStatus paymentStatus,
                   LocalDateTime date, long creditCardIdSource,
                   long creditCardUserIdSource, long creditCardIdDestination,
                   long creditCardUserIdDestination) {
        this.id = id;
        this.money = money;
        this.paymentStatus = paymentStatus;
        this.creditCardIdSource = creditCardIdSource;
        this.creditCardUserIdSource = creditCardUserIdSource;
        this.creditCardIdDestination = creditCardIdDestination;
        this.creditCardUserIdDestination = creditCardUserIdDestination;
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

    public long getCreditCardUserIdSource() {
        return creditCardUserIdSource;
    }

    public void setCreditCardUserIdSource(long creditCardUserIdSource) {
        this.creditCardUserIdSource = creditCardUserIdSource;
    }

    public long getCreditCardIdDestination() {
        return creditCardIdDestination;
    }

    public void setCreditCardIdDestination(long creditCardIdDestination) {
        this.creditCardIdDestination = creditCardIdDestination;
    }

    public long getCreditCardUserIdDestination() {
        return creditCardUserIdDestination;
    }

    public void setCreditCardUserIdDestination(long creditCardUserIdDestination) {
        this.creditCardUserIdDestination = creditCardUserIdDestination;
    }
}
