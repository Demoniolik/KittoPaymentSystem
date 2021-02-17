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

public class ShowMorePayments implements ServletCommand {
    private static final Logger logger = Logger.getLogger(ShowMorePayments.class);
    private PaymentService paymentService;
    private String mainPage;

    public ShowMorePayments() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing showing more payments command");
        String pageSizeParam = request.getParameter("limit");
        HttpSession session = request.getSession();
        String sortingCriteria = (String) session.getAttribute("sortingCriteria");
        String sortingOrder = (String) session.getAttribute("sortingOrder");

        int pageSize;
        if (pageSizeParam.equals("")) {
            pageSize = 15;
        } else {
           pageSize = Integer.parseInt(pageSizeParam) + 5;
        }

        //TODO: here you also need to add selector of card with witch you work
        List<CreditCard> creditCards = (List<CreditCard>) request.getSession().getAttribute("userCreditCards");
        List<Payment> payments;

        if (sortingCriteria == null || sortingOrder == null) {
            payments = paymentService.getAllPaymentsWithLimitOption(creditCards.get(0).getId(), pageSize);
        } else {
            payments = paymentService.getAllPaymentsSortedWithLimitOption(creditCards.get(0).getId(), pageSize,
                    sortingCriteria, sortingOrder);
        }

        request.setAttribute("pageSize", pageSize);
        request.setAttribute("maxPageSize", paymentService.getAmountOfCardPayments(creditCards.get(0).getId()));

        session.setAttribute("creditCardPayments", LoginCommand.wrapPaymentList(payments));

        return mainPage;
    }
}
