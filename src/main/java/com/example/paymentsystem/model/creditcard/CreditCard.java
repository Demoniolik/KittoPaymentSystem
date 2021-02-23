package com.example.paymentsystem.model.creditcard;

public class CreditCard {
    private long id;
    private long number;
    private String name;
    private double moneyOnCard;
    private int cvcCode;
    private boolean blocked;
    private long userId;

    public CreditCard() {

    }

    public CreditCard(long id, long number, String name, double moneyOnCard, int cvcCode, boolean blocked, long userId) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.moneyOnCard = moneyOnCard;
        this.cvcCode = cvcCode;
        this.blocked = blocked;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getCvcCode() {
        return cvcCode;
    }

    public void setCvcCode(int cvcCode) {
        this.cvcCode = cvcCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoneyOnCard() {
        return moneyOnCard;
    }

    public void setMoneyOnCard(double moneyOnCard) {
        this.moneyOnCard = moneyOnCard;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
