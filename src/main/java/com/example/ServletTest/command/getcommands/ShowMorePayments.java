package com.example.ServletTest.command.getcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.command.postcommands.LoginCommand;
import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
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

public class ShowMorePayments implements ServletCommand {
    private static final Logger logger = Logger.getLogger(ShowMorePayments.class);
    private final PaymentService paymentService;
    private final CreditCardService creditCardService;
    private final String mainPage;

    public ShowMorePayments() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
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
            payments = paymentService
                    .getAllPaymentsWithLimitOption(creditCardService.getCreditCardByNumber(
                            actualCardForSelectingPayments).getId(), paymentPageSize
                    );
        } else {
            payments = paymentService.getAllPaymentsSortedWithLimitOption(creditCards.get(0).getId(), paymentPageSize,
                    sortingCriteria, sortingOrder);
        }

        request.setAttribute("paymentPageSize", paymentPageSize);
        request.setAttribute("maxPaymentPageSize", paymentService.getAmountOfCardPayments(creditCards.get(0).getId()));

        session.setAttribute("creditCardPayments", LoginCommand.wrapPaymentList(payments));

        return mainPage;
    }
}
