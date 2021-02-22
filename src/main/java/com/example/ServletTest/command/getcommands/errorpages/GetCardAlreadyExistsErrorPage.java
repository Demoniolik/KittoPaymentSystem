package com.example.ServletTest.command.getcommands.errorpages;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCardAlreadyExistsErrorPage implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GetCardAlreadyExistsErrorPage.class);
    private String errorPage;

    public GetCardAlreadyExistsErrorPage() {
        MappingProperties properties = MappingProperties.getInstance();
        errorPage = properties.getProperty("errorPageCardAlreadyExists");
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing card already exists error page command");
        return errorPage;
    }
}
