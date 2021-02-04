package com.example.ServletTest.model.unblockingrequest;

public class UnblockingRequest {
    private long id;
    private long creditCardId;
    private long creditCardUserId;

    public UnblockingRequest() {}

    public UnblockingRequest(long id, long creditCardId, long creditCardUserId) {
        this.id = id;
        this.creditCardId = creditCardId;
        this.creditCardUserId = creditCardUserId;
    }

    public long getId() {
        return id;
    }

    public UnblockingRequest setId(long id) {
        this.id = id;
        return this;
    }

    public long getCreditCardId() {
        return creditCardId;
    }

    public UnblockingRequest setCreditCardId(long creditCardId) {
        this.creditCardId = creditCardId;
        return this;
    }

    public long getCreditCardUserId() {
        return creditCardUserId;
    }

    public UnblockingRequest setCreditCardUserId(long creditCardUserId) {
        this.creditCardUserId = creditCardUserId;
        return this;
    }
}
