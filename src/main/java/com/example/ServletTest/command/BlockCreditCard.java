package com.example.ServletTest.command;

import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class BlockCreditCard implements ServletCommand {
    private static final Logger logger = Logger.getLogger(BlockCreditCard.class);
    private CreditCardService creditCardService;
    private String mainPage;

    public BlockCreditCard() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing blocking credit card command");
        long cardId = Long.parseLong(request.getParameter("cardId"));
        creditCardService.changeBlockingStatusCreditCardById(cardId, 1); // 1 is to block card
        HttpSession session = request.getSession();
        long userId = ((User)session.getAttribute("user")).getId();
        List<CreditCard> userCards = creditCardService
                .getAllCreditCardsThatBelongToUserWithDefaultLimit(userId);
        session.setAttribute("userCreditCardsWithPagination", userCards);
        return mainPage;
    }
}
