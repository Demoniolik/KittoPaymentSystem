package com.example.ServletTest.command.postcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.unblockrequest.UnblockRequestDaoImpl;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.unblockingrequest.UnblockingRequest;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.unblockrequest.UnblockRequestService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUnblockRequest implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CreateUnblockRequest.class);
    private UnblockRequestService unblockRequestService;
    private CreditCardService creditCardService;
    private String personalCabinetPage;
    private String errorPage;

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
            return errorPage;
        }
        unblockingRequest.setDescription(descriptionParam);
        unblockingRequest.setRequestStatus(UnblockingRequest.RequestStatus.NOT_APPROVED);

        if (unblockRequestService.createUnblockRequest(unblockingRequest)) {
            return personalCabinetPage;
        }


        return errorPage;
    }
}
