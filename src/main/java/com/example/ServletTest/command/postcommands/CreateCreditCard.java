package com.example.ServletTest.command.postcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.creditcard.CreditCardBuilder;
import com.example.ServletTest.model.creditcard.cvccodegenerator.CvcCodeGenerator;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.List;

public class CreateCreditCard implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CreateCreditCard.class);
    private CreditCardService creditCardService;
    private String mainPage;
    private String errorPage;

    public CreateCreditCard() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPagePost");
        errorPage = properties.getProperty("errorPageCardAlreadyExistsPost");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing creating credit card command");
        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");

        long cardNumberValue = Long.parseLong(cardNumber);

        if (creditCardService.getCreditCardByNumber(cardNumberValue) != null) {
            return errorPage;
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

        if (creditCardService.createCreditCard(creditCard)) {
            List<CreditCard> creditCards = creditCardService.getAllUnblockedCreditCards(userId);
            session.setAttribute("userCreditCards", creditCards);
        }

        return mainPage;
    }
}
