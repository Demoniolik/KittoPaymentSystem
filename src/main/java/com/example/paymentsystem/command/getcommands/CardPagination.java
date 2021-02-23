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

public class CardPagination implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CardPagination.class);
    private final CreditCardService creditCardService;
    private final String mainPage;
    private final String errorPage;

    public CardPagination() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing card pagination command");

        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");
        int page;
        int pageSize = 4;

        if (pageSizeParam.equals("")) {
            page = 2;
        } else {
            page = Integer.parseInt(pageParam);
        }

        HttpSession session = request.getSession();
        String sortingCriteria = (String) session.getAttribute("sortingCriteria");
        String sortingOrder = (String) session.getAttribute("sortingOrder");
        long userId = ((User)session.getAttribute("user")).getId();

        int amountOfCards = 0;
        try {
            amountOfCards = creditCardService.getCountOfCardsThatBelongToUser(userId);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }
        int maxPage = (int)Math.ceil((double) amountOfCards / pageSize);

        List<CreditCard> userCreditCards;
        if (sortingCriteria == null || sortingOrder == null) {
            sortingCriteria = "id";
            sortingOrder = "ASC";
        }
        try {
            userCreditCards = creditCardService
                    .getAllCreditCardsByCriteriaWithLimit(userId, sortingCriteria, sortingOrder, page, pageSize);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("maxPage", maxPage);
        request.setAttribute("userCreditCardsWithPagination", userCreditCards);

        return mainPage;
    }
}
