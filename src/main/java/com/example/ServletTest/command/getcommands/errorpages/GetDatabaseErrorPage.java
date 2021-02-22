package com.example.ServletTest.command.getcommands.errorpages;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetDatabaseErrorPage implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GetDatabaseErrorPage.class);
    private String errorPage;

    public GetDatabaseErrorPage() {
        MappingProperties properties = MappingProperties.getInstance();
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing database get error page command");
        return errorPage;
    }
}
