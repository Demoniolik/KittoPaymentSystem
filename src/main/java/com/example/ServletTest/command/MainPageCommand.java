package com.example.ServletTest.command;

import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(MainPageCommand.class);
    private String mainPage;

    public MainPageCommand() {
        // TODO: Here we call services to work
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage"); // Check this moment
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // TODO: after login or registering you come here and go to the main page
        return mainPage;
    }
}
