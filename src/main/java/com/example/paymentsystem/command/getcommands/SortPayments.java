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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This command sorts payments using given sorting parameters
 */

public class SortPayments implements ServletCommand {
    private static final Logger logger = Logger.getLogger(SortPayments.class);
    private final PaymentService paymentService;
    private final CreditCardService creditCardService;
    private final String mainPage;
    private final String errorPage;

    public SortPayments() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing sorting payments by id command");

        String sortingCriteria = request.getParameter("paymentSortingCriteria");
        String sortingOrder = request.getParameter("paymentSortingOrder");

        Pattern pattern = Pattern.compile("\\b.{0,4}\\b");
        Matcher matcher = pattern.matcher(sortingCriteria);
        Matcher matcher2 = pattern.matcher(sortingOrder);

        if (!(matcher.find() && matcher2.find())) {
            request.setAttribute("errorCause", "Do not mess with link!");
            return errorPage;
        }

        HttpSession session = request.getSession();
        List<CreditCard> creditCards = (List<CreditCard>) session.getAttribute("userCreditCards");
        List<Payment> payments;

        Long actualCardForSelectingPayments = (Long) session.getAttribute("actualCardForSelectingPayments");

        if (actualCardForSelectingPayments == null) {
            try {
                payments =
                        paymentService.getListOfPaymentsSortedByCriteria(creditCards.get(0).getId(), sortingCriteria, sortingOrder);
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }
        } else {
            try {
                payments = paymentService
                        .getListOfPaymentsSortedByCriteria(
                                creditCardService.getCreditCardByNumber(actualCardForSelectingPayments).getId(),
                                sortingCriteria,
                                sortingOrder
                        );
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }
        }

        try {
            session.setAttribute("creditCardPayments", LoginCommand.wrapPaymentList(payments));
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        if (sortingOrder.equals("ASC")) {
            request.setAttribute("sorted", true);
            session.setAttribute("paymentSortingOrder", "ASC");
        } else {
            request.setAttribute("sorted", false);
            session.setAttribute("paymentSortingOrder", "DESC");
        }

        if (sortingCriteria.equals("id")) {
            session.setAttribute("paymentSortingCriteria", "id");
        } else {
            session.setAttribute("paymentSortingCriteria", "date");
        }

        return mainPage;
    }
}
