package com.example.paymentsystem.command.getcommands;

import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.command.ServletCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPageCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(RegisterPageCommand.class);
    private final String registrationPage;
    private final String mainPage;

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
