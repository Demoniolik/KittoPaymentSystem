package com.example.paymentsystem.command.getcommands;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.creditcard.CreditCard;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class BlockCreditCard implements ServletCommand {
    private static final Logger logger = Logger.getLogger(BlockCreditCard.class);
    private CreditCardService creditCardService;
    private String mainPage;
    private String errorPage;

    public BlockCreditCard() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing blocking credit card command");
        long cardId = Long.parseLong(request.getParameter("cardId"));

        //TODO: here you need to check if the card belongs to user, if not throw error page

        try {
            creditCardService.changeBlockingStatusCreditCardById(cardId, 1); // 1 is to block card
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        HttpSession session = request.getSession();
        long userId = ((User)session.getAttribute("user")).getId();
        List<CreditCard> userCards = null;

        try {
            userCards = creditCardService
                    .getAllCreditCardsThatBelongToUserWithDefaultLimit(userId);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        session.setAttribute("userCreditCardsWithPagination", userCards);
        return mainPage;
    }
}
