package com.example.ServletTest.command.getcommands.errorpages;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCardIsBlockedErrorPage implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GetCardIsBlockedErrorPage.class);
    private String errorPage;

    public GetCardIsBlockedErrorPage() {
        MappingProperties properties = MappingProperties.getInstance();
        errorPage = properties.getProperty("errorPageDestinationCardIsBlocked");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing card is blocked error page command");
        return errorPage;
    }
}
