package com.example.paymentsystem.command.admin;

import com.example.paymentsystem.dao.unblockrequest.UnblockRequestDaoImpl;
import com.example.paymentsystem.service.unblockrequest.UnblockRequestService;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.unblockingrequest.UnblockingRequest;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class unlocks user card with request that are send from users
 */

public class UnblockUserCard implements ServletCommand {
    private static final Logger logger = Logger.getLogger(UnblockUserCard.class);
    private final CreditCardService creditCardService;
    private final UnblockRequestService unblockRequestService;
    private final String adminPage;
    private final String errorPage;

    public UnblockUserCard() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        unblockRequestService =
                new UnblockRequestService(UnblockRequestDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        adminPage = properties.getProperty("adminPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing unblocking user card command");

        long cardId = Long.parseLong(request.getParameter("cardId"));

        try {
            creditCardService.changeBlockingStatusCreditCardById(cardId, 0); // 0 is to unblock card
            unblockRequestService.changeUnblockingRequestStatus(cardId, UnblockingRequest.RequestStatus.APPROVED);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        HttpSession session = request.getSession();

        try {
            session.setAttribute("unblockingRequests",
                    unblockRequestService.getUnapprovedUnblockingRequests());
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }
        return adminPage;
    }
}
