package com.example.paymentsystem.command.postcommands;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.creditcard.CreditCard;
import com.example.paymentsystem.model.creditcard.CreditCardBuilder;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import com.example.paymentsystem.model.creditcard.cvccodegenerator.CvcCodeGenerator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.List;

public class CreateCreditCard implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CreateCreditCard.class);
    private final CreditCardService creditCardService;
    private final String mainPage;
    private final String errorPage;
    private final String errorPageDataBase;

    public CreateCreditCard() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPagePost");
        errorPage = properties.getProperty("errorPageCardAlreadyExistsPost");
        errorPageDataBase = properties.getProperty("errorPageDatabasePost");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing creating credit card command");

        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");

        long cardNumberValue = Long.parseLong(cardNumber);

        try {
            if (creditCardService.getCreditCardByNumber(cardNumberValue) != null) {
                return errorPage;
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPageDataBase;
        }

        HttpSession session = request.getSession();
        long userId = ((User)session.getAttribute("user")).getId();

        CreditCard creditCard = new CreditCardBuilder()
                .setName(cardName)
                .setNumber(Long.parseLong(cardNumber))
                .setUserId(userId)
                .setBlocked(false)
                .setCvcCode(new CvcCodeGenerator(new SecureRandom()).getCvcCode()) // Tricky moment
                .build();

        try {
            if (creditCardService.createCreditCard(creditCard)) {
                List<CreditCard> creditCards = creditCardService.getAllUnblockedCreditCards(userId);
                session.setAttribute("userCreditCards", creditCards);
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPageDataBase;
        }

        return mainPage;
    }
}
