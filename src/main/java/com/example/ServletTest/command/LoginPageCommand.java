package com.example.ServletTest.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPageCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LoginPageCommand.class);
    private static String loginPage;
    private static String mainPage;

    public LoginPageCommand() {
        // TODO: Here we load jsp pages from properties file
        loginPage = "WEB-INF/index.jsp";
        mainPage = "WEB-INF/MainContent.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing login page command");
        String resultPage = loginPage;
        if (request.getSession().getAttribute("authorized") != null
        && request.getSession().getAttribute("authorized").equals(true)) {
            resultPage = mainPage;
        } // TODO: Here probably you should check if these values are just empty string
        else if (request.getParameter("login") == null || request.getParameter("password") == null) {
            logger.info("Returning to login page");
            resultPage = loginPage;
        }
        return resultPage;
    }
}
