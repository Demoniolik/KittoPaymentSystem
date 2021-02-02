package com.example.ServletTest.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(MainPageCommand.class);

    public MainPageCommand() {
        // TODO: Here we call services to work
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
