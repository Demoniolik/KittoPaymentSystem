package com.example.ServletTest.command.admin;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.user.UserService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ChangeUserStatus implements ServletCommand {
    private static final Logger logger = Logger.getLogger(ChangeUserStatus.class);
    private UserService userService;
    private String adminPage;

    public ChangeUserStatus() {
        userService = new UserService(UserDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        adminPage = properties.getProperty("adminPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing changing user status command");
        String option = request.getParameter("option");
        String userId = request.getParameter("userId");

        User user = userService.getUserById(Long.parseLong(userId));
        if (option.equals("unblock")) {
            user.setBlocked(false);
        } else {
            user.setBlocked(true);
        }
        userService.updateUserData(user);
        HttpSession session = request.getSession();
        List<User> allUsers = userService.getAllUsers();
        session.setAttribute("allUsers", allUsers);
        return adminPage;
    }
}
