package com.example.ServletTest.service.unblockrequest;

import com.example.ServletTest.dao.unblockrequest.UnblockRequestDao;
import com.example.ServletTest.model.unblockingrequest.UnblockingRequest;
import org.apache.log4j.Logger;

public class UnblockRequestService {
    private static final Logger logger = Logger.getLogger(UnblockRequestService.class);
    private UnblockRequestDao unblockRequestDao;

    public UnblockRequestService(UnblockRequestDao unblockRequestDao) {
        this.unblockRequestDao = unblockRequestDao;
    }

    public boolean createUnblockRequest(UnblockingRequest unblockingRequest) {
        logger.info("Creating unblocking request");
        return unblockRequestDao.save(unblockingRequest).getId() != 0;
    }
}
