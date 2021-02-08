package com.example.ServletTest.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CommandManager {
    private static final Logger logger = Logger.getLogger(CommandManager.class);
    private HashMap<String, ServletCommand> getCommands;
    private HashMap<String, ServletCommand> postCommands;

    public CommandManager() {
        logger.info("GET commands are loaded");
        getCommands = new HashMap<>();
        postCommands = new HashMap<>();
        // GET commands
        getCommands.put("/", new MainPageCommand());
        getCommands.put("/login", new LoginPageCommand());
        getCommands.put("/register", new RegisterPageCommand());
        getCommands.put("/getPayments", new PaymentPageCommand());


        //POST commands
        logger.info("POST commands are loaded");
        postCommands.put("/register", new RegisterCommand());
        postCommands.put("/login", new LoginCommand());
        postCommands.put("/replenishCreditCard", new ReplenishCreditCardCommand());
        postCommands.put("/creatTransfer", new CreateTransferCommand());

    }

    public ServletCommand getGetCommand(HttpServletRequest request) {
        String command = getMapping(request);
        if (getCommands.get(command) == null) {
            return getCommands.get("/");
        }
        return getCommands.get(command);
    }

    public ServletCommand getPostCommand(HttpServletRequest request) {
        String command = getMapping(request);
        if (postCommands.get(command) == null) {
            return postCommands.get("/");
        }
        return postCommands.get(command);
    }

    public String getMapping(HttpServletRequest request) {
        String mapping = request.getRequestURI().substring(request.getContextPath().length());
        if (mapping.endsWith("/")) {
            mapping = mapping.substring(0, mapping.length() - 1);
        }
        return mapping;
    }
}
