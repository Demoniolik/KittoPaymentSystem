package com.example.paymentsystem.command.getcommands.errorpages;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class gives error page that has attitude to the
 * database exception
 */

public class GetDatabaseErrorPage implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GetDatabaseErrorPage.class);
    private final String errorPage;

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
