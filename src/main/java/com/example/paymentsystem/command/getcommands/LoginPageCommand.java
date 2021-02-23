package com.example.paymentsystem.command.getcommands;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.creditcard.CreditCard;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.paymentsystem.command.postcommands.LoginCommand.prepareDataForUser;

/**
 * This class gives login page to the user
 */

public class LoginPageCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LoginPageCommand.class);
    private final CreditCardService creditCardService;
    private static String loginPage;
    private static String mainPage;
    private final String adminPage;
    private final String errorPage;

    public LoginPageCommand() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        loginPage = properties.getProperty("loginPage");
        adminPage = properties.getProperty("adminPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing login page command");

        String resultPage = loginPage;
        HttpSession session = request.getSession();

        if (session.getAttribute("authorized") != null
        && session.getAttribute("authorized").equals(true)) {
            if (session.getAttribute("role") != null &&
            session.getAttribute("role").equals("admin")) {
                return adminPage;
            }

            long userId = ((User)session.getAttribute("user")).getId();

            List<CreditCard> creditCards;
            List<CreditCard> creditCardsView;
            try {
                creditCards = creditCardService.getAllUnblockedCreditCards(userId);
                creditCardsView = creditCardService.getAllCreditCardsThatBelongToUserWithDefaultLimit(userId);
                prepareDataForUser(request, creditCards, creditCardsView);
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }

            resultPage = mainPage;
        }
        else if (request.getParameter("login") == null || request.getParameter("password") == null) {
            logger.info("Returning to login page");
            resultPage = loginPage;
        }
        return resultPage;
    }
}
