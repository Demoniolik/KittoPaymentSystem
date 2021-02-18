package com.example.ServletTest.command;

import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.payment.Payment;
import com.example.ServletTest.service.payment.PaymentService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortPayments implements ServletCommand {
    private static final Logger logger = Logger.getLogger(SortPayments.class);
    private PaymentService paymentService;
    private String mainPage;
    private String errorPage;

    public SortPayments() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        errorPage = properties.getProperty("errorPage");
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
            return errorPage;
        }

        HttpSession session = request.getSession();
        List<CreditCard> creditCards = (List<CreditCard>) session.getAttribute("userCreditCards");
        //TODO: Change it to the selected card
        List<Payment> payments =
                paymentService.getListOfPaymentsSortedByCriteria(creditCards.get(0).getId(), sortingCriteria, sortingOrder);
        session.setAttribute("creditCardPayments", LoginCommand.wrapPaymentList(payments));
        if (sortingOrder.equals("ASC")) {
            request.setAttribute("sorted", true);
            session.setAttribute("sortingOrder", "ASC");
        } else {
            request.setAttribute("sorted", false);
            session.setAttribute("sortingOrder", "DESC");
        }

        if (sortingCriteria.equals("id")) {
            session.setAttribute("paymentSortingCriteria", "id");
        } else {
            session.setAttribute("paymentSortingCriteria", "date");
        }

        return mainPage;
    }
}
