package com.example.ServletTest.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPageCommand implements ServletCommand {
    private static Logger logger = Logger.getLogger(RegisterPageCommand.class);
    private String registrationPage;
    private String mainPage;

    public RegisterPageCommand() {
        //TODO: Here we need to load pages from properties file
        registrationPage = "WEB-INF/registration.jsp";
        mainPage = "WEB-INF/MainContent.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing register get command");
        Boolean authorized = (Boolean) request.getSession().getAttribute("authorized");
        if (authorized != null && authorized) {
            return mainPage;
        }

        return registrationPage;
    }
}
