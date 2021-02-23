package com.example.paymentsystem.command.getcommands.errorpages;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class gives error page to the user
 * in case destination card of a transfer is blocked
 */

public class GetCardIsBlockedErrorPage implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GetCardIsBlockedErrorPage.class);
    private final String errorPage;

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
