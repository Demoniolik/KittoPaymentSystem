package com.example.paymentsystem.model.payment;

public enum PaymentCategory {
    TRANSFER_TO_CARD("transfer"),
    REPLENISHING_MOBILE_PHONE("replenishing mobile phone"),
    REQUISITE("requisite"),
    UTILITIES("utilities"),
    CHARITY("charity");

    String category;

    public String getCategory() {
        return category;
    }

    PaymentCategory(String category) {
        this.category = category;
    }

}
