package com.example.ServletTest.servlet;

import com.example.ServletTest.connectionpool.BasicConnectionPool;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/sign_in")
public class MainServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MainServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        session.setAttribute("hello-message", "hello world");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        req.getSession().setAttribute("user_data", login + " " + password);
        Connection connection = null;
        try {
            BasicConnectionPool connectionPool = BasicConnectionPool
                    .create();
            connection = connectionPool.getConnection();
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        String query = "SELECT * FROM user";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        System.out.println("Connected!");
        req.getRequestDispatcher("WEB-INF/MainContent.jsp").forward(req, resp);
    }
}