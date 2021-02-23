package com.example.paymentsystem.command.admin;

import com.example.paymentsystem.dao.unblockrequest.UnblockRequestDaoImpl;
import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.dao.user.UserDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.service.unblockrequest.UnblockRequestService;
import com.example.paymentsystem.service.user.UserService;
import com.example.paymentsystem.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This class gives access to the admin page
 * if verifies user role in case it's not admin
 */

public class GoToAdminPage implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GoToAdminPage.class);
    private final UserService userService;
    private final UnblockRequestService unblockRequestService;
    private final String adminPage;
    private final String errorPage;

    public GoToAdminPage() {
        userService = new UserService(UserDaoImpl.getInstance());
        unblockRequestService = new UnblockRequestService(UnblockRequestDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        adminPage = properties.getProperty("adminPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing get admin page command");

        HttpSession session = request.getSession();
        List<User> allUsers;

        try {
            allUsers = userService.getAllUsers();
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        session.setAttribute("allUsers", allUsers);

        try {
            session.setAttribute("unblockingRequests",
                    unblockRequestService.getUnapprovedUnblockingRequests());
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        return adminPage;
    }
}
