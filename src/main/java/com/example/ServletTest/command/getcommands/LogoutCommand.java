package com.example.ServletTest.command.getcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LogoutCommand.class);
    private String loginPage;

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
