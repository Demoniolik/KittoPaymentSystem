package com.example.paymentsystem.command.postcommands;

import com.example.paymentsystem.dao.user.UserDaoImpl;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.model.user.UserBuilder;
import com.example.paymentsystem.model.user.UserType;
import com.example.paymentsystem.service.user.UserService;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.exception.DatabaseException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(RegisterCommand.class);
    private static UserService userService;
    private static String registrationPage;
    private static String mainPage;
    private final String errorPage;

    public RegisterCommand() {
        userService = new UserService(UserDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        registrationPage = properties.getProperty("registrationPagePost");
        mainPage = properties.getProperty("mainPagePost");
        errorPage = properties.getProperty("errorPageDatabasePost");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing registration command");

        String resultPage = registrationPage;
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        logger.info("New user registration");

        if (firstName != null && lastName != null
                && login != null && password != null
                && confirmPassword != null) {
            User user = new UserBuilder().setFirstName(firstName)
                    .setLastName(lastName)
                    .setLogin(login)
                    .setPassword(password)
                    .setUserType(UserType.USER)
                    .build();

            try {
                if (userService.register(user)) {
                    resultPage = mainPage;
                    LoginCommand.putUserToSession(request, user);
                }
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }
        }
        return resultPage;
    }
}
