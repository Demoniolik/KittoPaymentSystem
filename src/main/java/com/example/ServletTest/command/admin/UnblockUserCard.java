package com.example.ServletTest.command.admin;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.unblockrequest.UnblockRequestDaoImpl;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.unblockingrequest.UnblockingRequest;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.unblockrequest.UnblockRequestService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnblockUserCard implements ServletCommand {
    private static final Logger logger = Logger.getLogger(UnblockUserCard.class);
    private CreditCardService creditCardService;
    private UnblockRequestService unblockRequestService;
    private String adminPage;
    private String errorPage;

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
        } catch (DatabaseException e) {
            return errorPage;
        }

        try {
            unblockRequestService.changeUnblockingRequestStatus(cardId, UnblockingRequest.RequestStatus.APPROVED);
        } catch (DatabaseException e) {
            return errorPage;
        }
        HttpSession session = request.getSession();
        try {
            session.setAttribute("unblockingRequests",
                    unblockRequestService.getUnapprovedUnblockingRequests());
        } catch (DatabaseException e) {
            return errorPage;
        }
        return adminPage;
    }
}
