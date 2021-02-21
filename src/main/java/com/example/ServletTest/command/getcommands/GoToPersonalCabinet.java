package com.example.ServletTest.command.getcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.user.UserService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GoToPersonalCabinet implements ServletCommand {
    private static final Logger logger = Logger.getLogger(GoToPersonalCabinet.class);
    private CreditCardService creditCardService;
    private UserService userService;
    private String personalCabinetPage;
    private String errorPage;

    public GoToPersonalCabinet() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        userService = new UserService(UserDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        personalCabinetPage = properties.getProperty("personalCabinetPage");
        //errorPage = properties.getProperty("userErrorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing personal cabinet command");
        HttpSession session = request.getSession();
        boolean isLogged = (boolean) session.getAttribute("authorized");
        if (isLogged) {
            long userId = ((User)session.getAttribute("user")).getId();

            List<CreditCard> userBlockedCards = creditCardService.getAllBlockedCreditCardsThatBelongToUser(userId);
            session.setAttribute("userBlockedCreditCards", userBlockedCards);
            return personalCabinetPage;
        }

        return errorPage;
    }
}
