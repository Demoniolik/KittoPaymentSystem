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

public class ShowMorePayments implements ServletCommand {
    private static final Logger logger = Logger.getLogger(ShowMorePayments.class);
    private final PaymentService paymentService;
    private final CreditCardService creditCardService;
    private final String mainPage;
    private final String errorPage;

    public ShowMorePayments() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
        errorPage = properties.getProperty("errorPageDatabase");
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing showing more payments command");

        String pageSizeParam = request.getParameter("limit");
        HttpSession session = request.getSession();
        String sortingCriteria = (String) session.getAttribute("paymentSortingCriteria");
        String sortingOrder = (String) session.getAttribute("paymentSortingOrder");

        int paymentPageSize;
        if (pageSizeParam.equals("")) {
            paymentPageSize = 15;
        } else {
           paymentPageSize = Integer.parseInt(pageSizeParam) + 5;
        }

        List<CreditCard> creditCards = (List<CreditCard>) request.getSession().getAttribute("userCreditCards");
        List<Payment> payments;
        Long actualCardForSelectingPayments = (Long) session.getAttribute("actualCardForSelectingPayments");
        if (actualCardForSelectingPayments == null) {
            actualCardForSelectingPayments = creditCards.get(0).getNumber();
        }

        if (sortingCriteria == null || sortingOrder == null) {
            try {
                payments = paymentService
                        .getAllPaymentsWithLimitOption(creditCardService.getCreditCardByNumber(
                                actualCardForSelectingPayments).getId(), paymentPageSize
                        );
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }
        } else {
            try {
                payments = paymentService.getAllPaymentsSortedWithLimitOption(creditCards.get(0).getId(), paymentPageSize,
                        sortingCriteria, sortingOrder);
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }
        }

        request.setAttribute("paymentPageSize", paymentPageSize);

        try {
            request.setAttribute("maxPaymentPageSize", paymentService.getAmountOfCardPayments(creditCards.get(0).getId()));
            session.setAttribute("creditCardPayments", LoginCommand.wrapPaymentList(payments));
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        return mainPage;
    }
}
