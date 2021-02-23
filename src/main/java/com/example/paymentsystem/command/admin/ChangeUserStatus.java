package com.example.paymentsystem.command.admin;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import com.example.paymentsystem.dao.user.UserDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.service.user.UserService;
import com.example.paymentsystem.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ChangeUserStatus implements ServletCommand {
    private static final Logger logger = Logger.getLogger(ChangeUserStatus.class);
    private final UserService userService;
    private final CreditCardService creditCardService;
    private final String adminPage;
    private final String errorPage;

    public ChangeUserStatus() {
        userService = new UserService(UserDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        adminPage = properties.getProperty("adminPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing changing user status command");

        String option = request.getParameter("option");
        String userId = request.getParameter("userId");

        User user = null;
        try {
            user = userService.getUserById(Long.parseLong(userId));
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        if (option.equals("unblock")) {
            user.setBlocked(false);
        } else {
            user.setBlocked(true);
        }

        HttpSession session = request.getSession();
        List<User> allUsers;

        try {
            userService.updateUserData(user);
            creditCardService.blockAllUserCards(user.getId());
            allUsers = userService.getAllUsers();
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        session.setAttribute("allUsers", allUsers);
        return adminPage;
    }
}
