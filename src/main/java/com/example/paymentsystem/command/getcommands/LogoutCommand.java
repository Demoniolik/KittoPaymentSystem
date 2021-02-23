package com.example.paymentsystem.command.getcommands;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This command invalidates the session so all the
 * authorization data is deleted
 */

public class LogoutCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LogoutCommand.class);
    private final String loginPage;

    public LogoutCommand() {
        MappingProperties properties = MappingProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing login out command");

        request.getSession().invalidate();
        return loginPage;
    }
}
