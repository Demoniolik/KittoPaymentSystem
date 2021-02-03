package com.example.ServletTest.command;

import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);
    private static UserService userService;
    private static String loginPage;
    private static String mainPage;

    public LoginCommand() {
        userService = new UserService(UserDaoImpl.getInstance());
        // TODO: here we have to load out jsp files
        loginPage = "login.jsp";
        mainPage = "WEB-INF/MainContent.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing logging command");
        String resultPage = loginPage;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login != null && password != null) {
            User user = userService.getUserByCredentials(login, password);
            if (user != null) {
                putUserToSession(request, user);

                //TODO: add all the accounts and cards to the user
                resultPage = mainPage;
            }else {
                request.setAttribute("idLogged", false);
            }

        }
        return resultPage;
    }

    static void putUserToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("login", user.getLogin());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());
        session.setAttribute("authorized", true);
        session.setAttribute("role", user.getUserType().name());
    }
}
