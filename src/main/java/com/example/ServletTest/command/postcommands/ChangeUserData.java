package com.example.ServletTest.command.postcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.user.UserService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeUserData implements ServletCommand {
    private static final Logger logger = Logger.getLogger(ChangeUserData.class);
    private final UserService userService;
    private final String personalCabinetPage;
    private final String errorPage;

    public ChangeUserData() {
        userService = new UserService(UserDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        personalCabinetPage = properties.getProperty("personalCabinetPagePost");
        errorPage = properties.getProperty("errorPageDatabasePost");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing changing data about user data");

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        //TODO: here you need to verify if passwords are matching

        if (!firstName.equals("")) {
            user.setFirstName(firstName);
        }
        if (!lastName.equals("")) {
            user.setLastName(lastName);
        }
        if (!login.equals("")) {
            user.setLogin(login);
        }
        if (!password.equals("")) {
            user.setPassword(password);
        }

        try {
            userService.updateUserData(user);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        return personalCabinetPage;
    }
}
