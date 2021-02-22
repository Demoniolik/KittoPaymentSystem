package com.example.ServletTest.service.unblockrequest;

import com.example.ServletTest.dao.unblockrequest.UnblockRequestDao;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.unblockingrequest.UnblockingRequest;
import org.apache.log4j.Logger;

import java.util.List;

public class UnblockRequestService {
    private static final Logger logger = Logger.getLogger(UnblockRequestService.class);
    private UnblockRequestDao unblockRequestDao;

    public UnblockRequestService(UnblockRequestDao unblockRequestDao) {
        this.unblockRequestDao = unblockRequestDao;
    }

    public boolean createUnblockRequest(UnblockingRequest unblockingRequest) {
        logger.info("Creating unblocking request");
        try {
            return unblockRequestDao.save(unblockingRequest).getId() != 0;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UnblockingRequest> getUnapprovedUnblockingRequests() throws DatabaseException {
        logger.info("Retrieving all unblocking and not approved request");
        return unblockRequestDao.getAllUnapprovedRequests();
    }

    public void changeUnblockingRequestStatus(long cardId, UnblockingRequest.RequestStatus status) throws DatabaseException {
        logger.info("Changing status of unblocking request");
        unblockRequestDao.changeUnblockingRequestStatus(cardId, status);
    }
}
