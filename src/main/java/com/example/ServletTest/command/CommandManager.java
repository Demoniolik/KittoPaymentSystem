package com.example.ServletTest.command;

import com.example.ServletTest.command.admin.ChangeUserStatus;
import com.example.ServletTest.command.admin.GoToAdminPage;
import com.example.ServletTest.command.admin.UnblockUserCard;
import com.example.ServletTest.command.getcommands.*;
import com.example.ServletTest.command.getcommands.errorpages.GetCardAlreadyExistsErrorPage;
import com.example.ServletTest.command.getcommands.errorpages.GetCardIsBlockedErrorPage;
import com.example.ServletTest.command.getcommands.errorpages.GetCardNotFoundErrorPage;
import com.example.ServletTest.command.getcommands.errorpages.GetDatabaseErrorPage;
import com.example.ServletTest.command.postcommands.*;
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
        getCommands.put("/changeLocale", new ChangeLocaleCommand());
        getCommands.put("/login", new LoginPageCommand());
        getCommands.put("/register", new RegisterPageCommand());
        getCommands.put("/sortPayments", new SortPayments());
        getCommands.put("/sortCards", new SortCards());
        getCommands.put("/showMorePayments", new ShowMorePayments());
        getCommands.put("/blockCreditCard", new BlockCreditCard()); // This is better to be a post method
        getCommands.put("/cardPagination", new CardPagination());
        getCommands.put("/goToPersonalCabinet", new GoToPersonalCabinet());
        getCommands.put("/logout", new LogoutCommand());
        getCommands.put("/preparePdfFile", new PreparePdfFile());
        getCommands.put("/selectPaymentsByCards", new SelectPaymentsByCard());

        //GET command for errors
        getCommands.put("/getCardNotFoundErrorPage", new GetCardNotFoundErrorPage());
        getCommands.put("/getCardIsBlockedErrorPage", new GetCardIsBlockedErrorPage());
        getCommands.put("/getCardAlreadyExistsErrorPage", new GetCardAlreadyExistsErrorPage());
        getCommands.put("/getDatabaseErrorPage", new GetDatabaseErrorPage());

        //GET commands for admin
        getCommands.put("/admin", new GoToAdminPage());
        getCommands.put("/admin/changeUserStatus", new ChangeUserStatus());
        getCommands.put("/admin/unblockUserCard", new UnblockUserCard());

        //POST commands
        logger.info("POST commands are loaded");
        postCommands.put("/register", new RegisterCommand());
        postCommands.put("/login", new LoginCommand());
        postCommands.put("/replenishCreditCard", new ReplenishCreditCardCommand());
        postCommands.put("/createTransfer", new CreateTransferCommand());
        postCommands.put("/createPayment", new CreatePaymentCommand());
        postCommands.put("/creatingNewCreditCard", new CreateCreditCard());
        postCommands.put("/changeUserData", new ChangeUserData());
        postCommands.put("/createUnblockRequest", new CreateUnblockRequest());

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
