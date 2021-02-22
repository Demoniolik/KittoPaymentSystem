package com.example.ServletTest.command.getcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.command.postcommands.LoginCommand;
import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.payment.Payment;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.payment.PaymentService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SelectPaymentsByCard implements ServletCommand {
    private static final Logger logger = Logger.getLogger(SelectPaymentsByCard.class);
    private PaymentService paymentService;
    private CreditCardService creditCardService;
    private String mainPage;
    private String errorPage;

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
        CreditCard card = null;
        try {
            card = creditCardService.getCreditCardByNumber(chosenCard);
        } catch (DatabaseException e) {
            return errorPage;
        }
        if (sortingCriteria == null) {
            sortingCriteria = "id";
            sortingOrder = "DESC";
        }
        List<Payment> payments =
                null;
        try {
            payments = paymentService.getListOfPaymentsSortedByCriteria(card.getId(), sortingCriteria, sortingOrder);
        } catch (DatabaseException e) {
            return errorPage;
        }

        try {
            session.setAttribute("creditCardPayments", LoginCommand.wrapPaymentList(payments));
        } catch (DatabaseException e) {
            return errorPage;
        }
        session.setAttribute("actualCardForSelectingPayments", chosenCard);
        return mainPage;
    }
}
