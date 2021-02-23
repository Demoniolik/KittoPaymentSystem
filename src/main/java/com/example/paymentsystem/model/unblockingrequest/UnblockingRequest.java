package com.example.paymentsystem.model.unblockingrequest;

public class UnblockingRequest {
    private long id;
    private String description;
    private long creditCardId;
    private RequestStatus requestStatus;

    public UnblockingRequest() {}

    public UnblockingRequest(long id, String description, long creditCardId, RequestStatus requestStatus) {
        this.id = id;
        this.creditCardId = creditCardId;
        this.requestStatus = requestStatus;
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

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public enum RequestStatus {
        NOT_APPROVED, APPROVED
    }

}
