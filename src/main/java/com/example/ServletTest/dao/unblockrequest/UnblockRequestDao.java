package com.example.ServletTest.dao.unblockrequest;

import com.example.ServletTest.dao.DAO;
import com.example.ServletTest.model.unblockingrequest.UnblockingRequest;

import java.util.List;

public interface UnblockRequestDao extends DAO<UnblockingRequest> {

    List<UnblockingRequest> getAllUnapprovedRequests();

    void changeUnblockingRequestStatus(long cardId, UnblockingRequest.RequestStatus status);
}
