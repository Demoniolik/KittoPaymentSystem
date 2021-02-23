package com.example.ServletTest.command.admin;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.dao.unblockrequest.UnblockRequestDaoImpl;
import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.unblockrequest.UnblockRequestService;
import com.example.ServletTest.service.user.UserService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

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
