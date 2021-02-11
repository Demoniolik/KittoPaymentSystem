package com.example.ServletTest.command;

import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.payment.Payment;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.payment.PaymentService;
import com.example.ServletTest.service.user.UserService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);
    private static UserService userService;
    private static CreditCardService creditCardService;
    private static PaymentService paymentService;
    private static String loginPage;
    private static String mainPage;

    public LoginCommand() {
        userService = new UserService(UserDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        // TODO: here we have to load out jsp files
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        loginPage = properties.getProperty("loginPage");
        //adminPage = "WEB-INF/admin/admin.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing logging command");
        String resultPage = loginPage;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login != null && password != null) {
            User user = userService.getUserByCredentials(login, password);
            if (user != null) {
                putUserToSession(request, user);
                List<CreditCard> creditCards = creditCardService.getAllCreditCards(user.getId());
                //TODO: add all the accounts and cards to the user
                HttpSession session = request.getSession();
                session.setAttribute("userCreditCards", creditCards);
                List<Payment> payments = paymentService
                        .getListOfPaymentsThatBelongToCreditCard(creditCards.get(0).getId());
                session.setAttribute("creditCardPayments", payments);
                session.setAttribute("categories", paymentService.getAllCategories());
                resultPage = mainPage;
            }else {
                request.setAttribute("idLogged", false);
            }

        }
        return resultPage;
    }

    static void putUserToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("authorized", true);
    }
}
