package com.example.paymentsystem.dao.unblockrequest;

import com.example.paymentsystem.dao.DAO;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.unblockingrequest.UnblockingRequest;

import java.util.List;

public interface UnblockRequestDao extends DAO<UnblockingRequest> {

    List<UnblockingRequest> getAllUnapprovedRequests() throws DatabaseException;

    void changeUnblockingRequestStatus(long cardId, UnblockingRequest.RequestStatus status) throws DatabaseException;
}
