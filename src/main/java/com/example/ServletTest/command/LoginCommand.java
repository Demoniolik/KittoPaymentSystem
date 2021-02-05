package com.example.ServletTest.command;

import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.user.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);
    private static UserService userService;
    private static CreditCardService creditCardService;
    private static String loginPage;
    private static String mainPage;

    public LoginCommand() {
        userService = new UserService(UserDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        // TODO: here we have to load out jsp files
        loginPage = "WEB-INF/index.jsp";
        mainPage = "WEB-INF/MainContent.jsp";
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

                //TODO: add all the accounts and cards to the user
                HttpSession session = request.getSession();
                session.setAttribute("user_credit_cards", creditCardService.getAllCreditCards(user.getId()));
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
