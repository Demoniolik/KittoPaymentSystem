package com.example.ServletTest.model.creditcard;

public class CreditCardBuilder {
    private long id;
    private long number;
    private String name;
    private double moneyOnCard;
    private boolean blocked;
    private long userId;

    public CreditCardBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public CreditCardBuilder setNumber(long number) {
        this.number = number;
        return this;
    }

    public CreditCardBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CreditCardBuilder setMoneyOnCard(double moneyOnCard) {
        this.moneyOnCard = moneyOnCard;
        return this;
    }

    public CreditCardBuilder setBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public CreditCardBuilder setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public CreditCard build() {
        return new CreditCard(id, number, name, moneyOnCard, blocked, userId);
    }
}
