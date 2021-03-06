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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This command sort cards with given sorting parameters
 */

public class SortCards implements ServletCommand {
    private static final Logger logger = Logger.getLogger(SortCards.class);
    private final CreditCardService creditCardService;
    private final String mainPage;
    private final String errorPage;

    public SortCards() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing sorting credit cards command");

        //TODO: Refactor it
        String sortingCriteria = request.getParameter("sortingCriteria");
        String sortingOrder = request.getParameter("sortingOrder");

        Pattern pattern = Pattern.compile("\\b.{0,4}\\b");
        Matcher matcher = pattern.matcher(sortingCriteria);
        Matcher matcher2 = pattern.matcher(sortingOrder);

        if (!(matcher.find() && matcher2.find())) {
            return errorPage;
        }

        HttpSession session = request.getSession();
        long currentUserId = ((User)session.getAttribute("user")).getId();
        String pageSizeParam = (String) session.getAttribute("pageSize");
        String pageParam = (String) session.getAttribute("page");

        int pageSize;
        int page;

        if (pageSizeParam == null || pageParam == null) {
            pageSize = 4;
            page = 1;
        } else {
            pageSize = Integer.parseInt(pageSizeParam);
            page = Integer.parseInt(pageParam);
        }

        List<CreditCard> creditCards;
        try {
            creditCards = creditCardService
                    .getAllCreditCardsByCriteriaWithLimit(currentUserId ,sortingCriteria, sortingOrder, page, pageSize);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }
        session.setAttribute("userCreditCardsWithPagination", creditCards);

        //TODO: Refactor it

        if (sortingCriteria.equals("number") && sortingOrder.equals("ASC")) {
            request.setAttribute("sortedByNumber", true);
        } else if (sortingCriteria.equals("number")) {
            request.setAttribute("sortedByNumber", false);
        } else if (sortingCriteria.equals("name") && sortingOrder.equals("ASC")) {
            request.setAttribute("sortedByName", true);
        } else if (sortingCriteria.equals("name")) {
            request.setAttribute("sortedByName", false);
        } else if (sortingCriteria.equals("money_on_card") && sortingOrder.equals("ASC")) {
            request.setAttribute("sortedByAmount", true);
        } else {
            request.setAttribute("sortedByAmount", false);
        }

        session.setAttribute("sortingCriteria", sortingCriteria);
        session.setAttribute("sortingOrder", sortingOrder);

        return mainPage;
    }
}
