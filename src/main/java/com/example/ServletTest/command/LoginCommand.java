package com.example.ServletTest.command;

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
        // TODO: here we have to load out jsp files
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing logging command");
        String resultPage = loginPage;
        if (request.getParameter("email") != null && request.getParameter("password") != null) {
            User user = userService.getUserByCredentials(request.getParameter("email"),
                                                            request.getParameter("password"));
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("login", user.getLogin());
                session.setAttribute("firstName", user.getFirstName());
                session.setAttribute("lastName", user.getLastName());
                session.setAttribute("authorized", true);
                session.setAttribute("role", user.getUserType().name());

                //TODO: add all the accounts and cards to the user
                resultPage = mainPage;
            }else {
                request.setAttribute("idLogged", false);
            }

        }
        return resultPage;
    }
}
