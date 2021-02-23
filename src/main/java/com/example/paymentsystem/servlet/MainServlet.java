package com.example.paymentsystem.servlet;

import com.example.paymentsystem.command.CommandManager;
import com.example.paymentsystem.command.ServletCommand;
import org.apache.log4j.Logger;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/Servlet")
public class MainServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MainServlet.class);
    private CommandManager commandManager;

    @Override
    public void init() throws ServletException {
        logger.info("Initializing servlet");
        commandManager = new CommandManager();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("Processing GET request");
        ServletCommand command = commandManager.getGetCommand(request);
        String loadedPage = command.execute(request, response);
        request.getRequestDispatcher(loadedPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Processing POST request");
        ServletCommand command = commandManager.getPostCommand(request);
        String loadedMapping = command.execute(request, response);
        response.sendRedirect(request.getContextPath() + loadedMapping);
    }
}