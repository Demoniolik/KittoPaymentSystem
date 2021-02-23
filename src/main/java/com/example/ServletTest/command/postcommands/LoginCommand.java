package com.example.ServletTest.command.postcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.command.admin.GoToAdminPage;
import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.payment.Payment;
import com.example.ServletTest.model.user.UserType;
import com.example.ServletTest.view.PaymentWrapper;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.payment.PaymentService;
import com.example.ServletTest.service.user.UserService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class LoginCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);
    private static UserService userService;
    private static CreditCardService creditCardService;
    private static PaymentService paymentService;
    private static String loginPage;
    private static String mainPage;
    private static String adminPage;
    private final String errorPage;

    public LoginCommand() {
        userService = new UserService(UserDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPagePost");
        loginPage = properties.getProperty("loginPagePost");
        adminPage = properties.getProperty("adminPagePost");
        errorPage = properties.getProperty("errorPageDatabasePost");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing logging command");

        String resultPage = loginPage;
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login != null && password != null) {
            User user;
            try {
                user = userService.getUserByCredentials(login, password);
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }
            if (user != null && user.getUserType() == UserType.ADMIN) {
                logger.info("Redirecting to admin page");
                putAdminToSession(request, user);
                return adminPage;
            }
            if (user != null) {
                putUserToSession(request, user);
                List<CreditCard> creditCards;
                List<CreditCard> creditCardsWithPagination;

                try {
                    creditCards = creditCardService.getAllUnblockedCreditCards(user.getId());
                    creditCardsWithPagination = creditCardService.getAllCreditCardsThatBelongToUserWithDefaultLimit(user.getId());
                    prepareDataForUser(request, creditCards, creditCardsWithPagination);
                } catch (DatabaseException e) {
                    request.setAttribute("errorCause", e.getMessage());
                    return errorPage;
                }

                resultPage = mainPage;
            } else {
                request.setAttribute("idLogged", false);
            }
        }
        return resultPage;
    }

    public static void prepareDataForUser(HttpServletRequest request, List<CreditCard> creditCards,
                                          List<CreditCard> creditCardWithPagination) throws DatabaseException {
        HttpSession session = request.getSession();
        session.setAttribute("userCreditCards", creditCards);
        session.setAttribute("userCreditCardsWithPagination", creditCardWithPagination);
        List<Payment> payments = paymentService
                .getListOfPaymentsThatBelongToCreditCard(creditCardWithPagination.get(0).getId());
        session.setAttribute("creditCardPayments", wrapPaymentList(payments));
        List<String> paymentCategories = paymentService.getAllCategories();
        paymentCategories.remove(0);
        session.setAttribute("categories", paymentCategories);
    }

    public static void putUserToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("authorized", true);
    }

    public static void putAdminToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("role", "admin");
        session.setAttribute("authorized", true);
    }

    public static List<PaymentWrapper> wrapPaymentList(List<Payment> payments) throws DatabaseException {
        logger.info("Wrapping payment");
        List<PaymentWrapper> paymentWrappers = new ArrayList<>();
        for (Payment payment : payments) {
            paymentWrappers.add(new PaymentWrapper(payment));
        }
        return paymentWrappers;
    }
}
