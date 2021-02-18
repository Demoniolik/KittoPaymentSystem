package com.example.ServletTest.command.admin;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.dao.unblockrequest.UnblockRequestDaoImpl;
import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.service.unblockrequest.UnblockRequestService;
import com.example.ServletTest.service.user.UserService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToAdminPage implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GoToAdminPage.class);
    private UserService userService;
    private UnblockRequestService unblockRequestService;
    private String adminPage;
    private String errorPage;

    public GoToAdminPage() {
        userService = new UserService(UserDaoImpl.getInstance());
        unblockRequestService = new UnblockRequestService(UnblockRequestDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        adminPage = properties.getProperty("adminPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return adminPage;
    }
}
