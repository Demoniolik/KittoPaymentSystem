package com.example.ServletTest.command;

import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.util.MappingProperties;
import com.sun.istack.internal.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReplenishCreditCardCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(ReplenishCreditCardCommand.class);
    private CreditCardService creditCardService;
    private String mainPage;

    public ReplenishCreditCardCommand() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing replenishing credit card command");
        double replenishMoney = Double.parseDouble(request.getParameter("replenishMoney"));
        // TODO: here you need to verify money to be positive number
        long cardNumber = Long.parseLong(request.getParameter("chosenCreditCard"));
        if (creditCardService.replenishCreditCard(cardNumber, replenishMoney)) {
            logger.info("Credit card was replenished");
        }else {
            logger.info("credit card was not replenished");
            // TODO: throw exception and say user that card wasn't replenished
        }
        HttpSession session = request.getSession();
        long userId = ((User)session.getAttribute("user")).getId();
        session.setAttribute("user_credit_cards",
                creditCardService.getAllUnblockedCreditCards(userId));
        return mainPage;
    }
}
