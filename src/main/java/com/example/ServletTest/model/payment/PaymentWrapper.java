package com.example.ServletTest.model.payment;

import java.time.LocalDateTime;

public class PaymentWrapper {
    private final long id;
    private final double money;
    private final PaymentStatus paymentStatus;
    private final LocalDateTime date;
    private final long sourceCreditCardNumber;
    private final long destinationCreditCardNumber;

    private PaymentWrapper(Builder builder) {
        this.id = builder.id;
        this.money = builder.money;
        this.paymentStatus = builder.paymentStatus;
        this.date = builder.date;
        this.sourceCreditCardNumber = builder.sourceCreditCardNumber;
        this.destinationCreditCardNumber = builder.destinationCreditCardNumber;
    }

    public static class Builder {
        private long id;
        private double money;
        private PaymentStatus paymentStatus;
        private LocalDateTime date;
        private long sourceCreditCardNumber;
        private long destinationCreditCardNumber;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setMoney(double money) {
            this.money = money;
            return this;
        }

        public Builder setPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder setDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder setSourceCreditCardNumber(long sourceCreditCardNumber) {
            this.sourceCreditCardNumber = sourceCreditCardNumber;
            return this;
        }

        public Builder setDestinationCreditCardNumber(long destinationCreditCardNumber) {
            this.destinationCreditCardNumber = destinationCreditCardNumber;
            return this;
        }

        public PaymentWrapper build() {
            return new PaymentWrapper(this);
        }
    }

    public long getId() {
        return id;
    }

    public double getMoney() {
        return money;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public long getSourceCreditCardNumber() {
        return sourceCreditCardNumber;
    }

    public long getDestinationCreditCardNumber() {
        return destinationCreditCardNumber;
    }
}
