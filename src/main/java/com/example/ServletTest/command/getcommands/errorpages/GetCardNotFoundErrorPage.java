package com.example.ServletTest.command.getcommands.errorpages;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCardNotFoundErrorPage implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GetCardNotFoundErrorPage.class);
    private String errorPage;

    public GetCardNotFoundErrorPage() {
        MappingProperties properties = MappingProperties.getInstance();
        errorPage = properties.getProperty("errorPageDestinationCardIsNotFound");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing error page card not found command");
        return errorPage;
    }
}
