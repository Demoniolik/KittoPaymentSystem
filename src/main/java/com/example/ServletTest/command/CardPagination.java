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

public class CardPagination implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CardPagination.class);
    private CreditCardService creditCardService;
    private String mainPage;

    public CardPagination() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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

        int amountOfCards = creditCardService.getCountOfCardsThatBelongToUser(userId);
        int maxPage = (int)Math.ceil((double) amountOfCards / pageSize);

        List<CreditCard> userCreditCards;
        if (sortingCriteria == null || sortingOrder == null) {
            sortingCriteria = "id";
            sortingOrder = "ASC";
        }
        userCreditCards = creditCardService
                .getAllCreditCardsByCriteriaWithLimit(userId, sortingCriteria, sortingOrder, page, pageSize);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("maxPage", maxPage);
        request.setAttribute("userCreditCardsWithPagination", userCreditCards);

        return mainPage;
    }
}
