package com.example.paymentsystem.command.getcommands;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.command.postcommands.LoginCommand;
import com.example.paymentsystem.dao.payment.PaymentDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.creditcard.CreditCard;
import com.example.paymentsystem.model.payment.Payment;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.service.payment.PaymentService;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This command selects payments by card
 * data goes from a selector on a main page
 */

public class SelectPaymentsByCard implements ServletCommand {
    private static final Logger logger = Logger.getLogger(SelectPaymentsByCard.class);
    private final PaymentService paymentService;
    private final CreditCardService creditCardService;
    private final String mainPage;
    private final String errorPage;

    public SelectPaymentsByCard() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing select payments by card number command");

        long chosenCard = Long.parseLong(request.getParameter("actualCardForSelectingPayments"));
        HttpSession session = request.getSession();
        String sortingCriteria = (String) session.getAttribute("paymentSortingCriteria");
        String sortingOrder = (String) session.getAttribute("paymentSortingOrder");
        CreditCard card;

        try {
            card = creditCardService.getCreditCardByNumber(chosenCard);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }
        if (sortingCriteria == null) {
            sortingCriteria = "id";
            sortingOrder = "DESC";
        }
        List<Payment> payments;
        try {
            payments = paymentService.getListOfPaymentsSortedByCriteria(card.getId(), sortingCriteria, sortingOrder);
            session.setAttribute("creditCardPayments", LoginCommand.wrapPaymentList(payments));
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        session.setAttribute("actualCardForSelectingPayments", chosenCard);
        return mainPage;
    }
}
