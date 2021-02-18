package com.example.ServletTest.model.unblockingrequest;

public class UnblockingRequest {
    private long id;
    private String description;
    private long creditCardId;

    public UnblockingRequest() {}

    public UnblockingRequest(long id, String description, long creditCardId) {
        this.id = id;
        this.creditCardId = creditCardId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(long creditCardId) {
        this.creditCardId = creditCardId;
    }

}
