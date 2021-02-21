package com.example.ServletTest.command.getcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.command.admin.GoToAdminPage;
import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.payment.PaymentService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.ServletTest.command.postcommands.LoginCommand.prepareDataForUser;

public class LoginPageCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LoginPageCommand.class);
    private CreditCardService creditCardService;
    private PaymentService paymentService;
    private static String loginPage;
    private static String mainPage;
    private String adminPage;

    public LoginPageCommand() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        loginPage = properties.getProperty("loginPage");
        adminPage = properties.getProperty("adminPage");
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

            List<CreditCard> creditCards = creditCardService.getAllUnblockedCreditCards(userId);
            List<CreditCard> creditCardsView =
                    creditCardService.getAllCreditCardsThatBelongToUserWithDefaultLimit(userId);

            prepareDataForUser(request, creditCards, creditCardsView);
            resultPage = mainPage;
        } // TODO: Here probably you should check if these values are just empty string
        else if (request.getParameter("login") == null || request.getParameter("password") == null) {
            logger.info("Returning to login page");
            resultPage = loginPage;
        }
        return resultPage;
    }
}
