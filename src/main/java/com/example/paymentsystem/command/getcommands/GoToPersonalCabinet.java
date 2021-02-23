package com.example.paymentsystem.command.getcommands;

import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import com.example.paymentsystem.dao.user.UserDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.creditcard.CreditCard;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.service.user.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This class gives access to the personal cabinet page
 */

public class GoToPersonalCabinet implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GoToPersonalCabinet.class);
    private final CreditCardService creditCardService;
    private UserService userService; // For future use
    private final String personalCabinetPage;
    private final String errorPage;

    public GoToPersonalCabinet() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        userService = new UserService(UserDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        personalCabinetPage = properties.getProperty("personalCabinetPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing personal cabinet command");

        HttpSession session = request.getSession();
        boolean isLogged = (boolean) session.getAttribute("authorized");

        if (isLogged) {
            long userId = ((User)session.getAttribute("user")).getId();

            List<CreditCard> userBlockedCards = null;
            try {
                userBlockedCards = creditCardService.getAllBlockedCreditCardsThatBelongToUser(userId);
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }
            session.setAttribute("userBlockedCreditCards", userBlockedCards);
            return personalCabinetPage;
        }
        //TODO: authorization error
        return errorPage;
    }
}
