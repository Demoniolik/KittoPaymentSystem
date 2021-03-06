package com.example.paymentsystem.command.postcommands;

import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.sun.istack.internal.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This command changes amount of money on card
 * all the data comes via POST method
 */

public class ReplenishCreditCardCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(ReplenishCreditCardCommand.class);
    private final CreditCardService creditCardService;
    private final String mainPage;
    private final String errorPage;

    public ReplenishCreditCardCommand() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();

        mainPage = properties.getProperty("mainPagePost");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing replenishing credit card command");

        double replenishMoney = Double.parseDouble(request.getParameter("replenishMoney"));
        // TODO: here you need to verify money to be positive number
        long cardNumber = Long.parseLong(request.getParameter("chosenCreditCard"));
        try {
            if (creditCardService.replenishCreditCard(cardNumber, replenishMoney)) {
                logger.info("Credit card was replenished");
            }else {
                logger.info("credit card was not replenished");
                // TODO: throw exception and say user that card wasn't replenished
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        HttpSession session = request.getSession();
        long userId = ((User)session.getAttribute("user")).getId();

        try {
            session.setAttribute("user_credit_cards",
                    creditCardService.getAllUnblockedCreditCards(userId));
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }
        return mainPage;
    }
}
