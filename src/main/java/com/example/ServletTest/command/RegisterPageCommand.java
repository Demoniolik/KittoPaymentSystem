package com.example.ServletTest.command;

import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPageCommand implements ServletCommand {
    private static Logger logger = Logger.getLogger(RegisterPageCommand.class);
    private String registrationPage;
    private String mainPage;

    public RegisterPageCommand() {
        MappingProperties properties = MappingProperties.getInstance();
        registrationPage = properties.getProperty("registrationPage");
        mainPage = properties.getProperty("mainPage");
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
