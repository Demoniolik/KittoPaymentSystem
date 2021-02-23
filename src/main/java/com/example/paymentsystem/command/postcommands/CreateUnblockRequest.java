package com.example.paymentsystem.command.postcommands;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.dao.unblockrequest.UnblockRequestDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.service.unblockrequest.UnblockRequestService;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import com.example.paymentsystem.model.unblockingrequest.UnblockingRequest;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUnblockRequest implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CreateUnblockRequest.class);
    private final UnblockRequestService unblockRequestService;
    private final CreditCardService creditCardService;
    private final String personalCabinetPage;
    private final String errorPage;

    public CreateUnblockRequest() {
        unblockRequestService = new UnblockRequestService(UnblockRequestDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        personalCabinetPage = properties.getProperty("personalCabinetPagePost");
        errorPage = properties.getProperty("errorPageDatabasePost");
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing creating unblocking request for credit card");

        String cardNumberParam = request.getParameter("chosenCard");
        String descriptionParam = request.getParameter("reasonDescription");

        long cardNumber = Long.parseLong(cardNumberParam);

        UnblockingRequest unblockingRequest
                = new UnblockingRequest();
        try {
            unblockingRequest.setCreditCardId(creditCardService
                    .getCreditCardByNumber(cardNumber).getId());
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }
        unblockingRequest.setDescription(descriptionParam);
        unblockingRequest.setRequestStatus(UnblockingRequest.RequestStatus.NOT_APPROVED);

        try {
            if (unblockRequestService.createUnblockRequest(unblockingRequest)) {
                return personalCabinetPage;
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        return errorPage;
    }
}
