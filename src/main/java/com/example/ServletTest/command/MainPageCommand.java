package com.example.ServletTest.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(MainPageCommand.class);
    private String mainPage;

    public MainPageCommand() {
        // TODO: Here we call services to work
        mainPage = "WEB-INF/index.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return mainPage;
    }
}
