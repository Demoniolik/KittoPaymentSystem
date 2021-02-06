package com.example.ServletTest.command;

import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginPageCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LoginPageCommand.class);
    private CreditCardService creditCardService;
    private static String loginPage;
    private static String mainPage;

    public LoginPageCommand() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        // TODO: Here we load jsp pages from properties file
        loginPage = "index.jsp";
        mainPage = "WEB-INF/MainContent.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing login page command");
        String resultPage = loginPage;
        if (request.getSession().getAttribute("authorized") != null
        && request.getSession().getAttribute("authorized").equals(true)) {
            HttpSession session = request.getSession();
            session.setAttribute("user_credit_cards",
                    creditCardService.getAllCreditCards(((User)session.getAttribute("user")).getId()));
            resultPage = mainPage;
        } // TODO: Here probably you should check if these values are just empty string
        else if (request.getParameter("login") == null || request.getParameter("password") == null) {
            logger.info("Returning to login page");
            resultPage = loginPage;
        }
        return resultPage;
    }
}
