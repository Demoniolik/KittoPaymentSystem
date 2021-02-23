package com.example.paymentsystem.model.payment;

public enum PaymentStatus {
    PREPARED("prepared"),
    SENT("sent");

    private String status;

    public String getStatus() {
        return status;
    }

    PaymentStatus(String status) {
        this.status = status;
    }
}
