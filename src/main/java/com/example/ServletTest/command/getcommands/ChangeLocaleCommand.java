package com.example.ServletTest.command.getcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLocaleCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(ChangeLocaleCommand.class);
    private String mainPage;

    public ChangeLocaleCommand() {
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage"); // Check this moment
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing main page command");

        if(request.getParameter("locale") != null) {
            String locale = request.getParameter("locale");
            switch (locale) {
                case "en":
                    request.getSession().setAttribute("locale", "en");
                    break;
                case "ru":
                    request.getSession().setAttribute("locale", "ru");
                    break;
            }
        }
        return mainPage;
    }
}
