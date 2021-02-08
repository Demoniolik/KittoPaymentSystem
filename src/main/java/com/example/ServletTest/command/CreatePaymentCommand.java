package com.example.ServletTest.command;

import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.payment.Payment;
import com.example.ServletTest.model.payment.PaymentBuilder;
import com.example.ServletTest.model.payment.PaymentStatus;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.payment.PaymentService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CreatePaymentCommand implements ServletCommand{
    private static final Logger logger = Logger.getLogger(CreatePaymentCommand.class);
    private static PaymentService paymentService;
    private static CreditCardService creditCardService;
    private String mainPage;
    private static final Map<String, Long> paymentCategoryToCreditCardNumberAssociation = new HashMap<>();

    public CreatePaymentCommand() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        // TODO: Here you need to put some properties file for categories
        paymentCategoryToCreditCardNumberAssociation
                .put("replenishing mobile phone", 9999999999L);
        paymentCategoryToCreditCardNumberAssociation
                .put("requisite", 1111111111L);
        paymentCategoryToCreditCardNumberAssociation
                .put("utilities", 1010101010L);
        paymentCategoryToCreditCardNumberAssociation
                .put("charity", 9090909090L);
        // TODO: Here you need to load data from properties file
        mainPage = "WEB-INF/MainContent.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing payment creation command");
        double moneyToPay = Double.parseDouble(request.getParameter("moneyToPay"));
        long sourceNumber = Long.parseLong(request.getParameter("sourceNumber"));
        String categoryOfPayment = request.getParameter("chosenCategory");
        long destinationCreditCardNumber =
                paymentCategoryToCreditCardNumberAssociation.get(categoryOfPayment);
        CreditCard sourceCreditCard = creditCardService.getCreditCardByNumber(sourceNumber);
        CreditCard destinationCreditCard = creditCardService.getCreditCardByNumber(destinationCreditCardNumber);
        Payment payment = new PaymentBuilder().setMoney(moneyToPay)
                .setCreditCardIdSource(sourceCreditCard.getId())
                .setCreditCardIdDestination(destinationCreditCard.getId())
                .setDate(LocalDateTime.now())
                .setPaymentStatus(PaymentStatus.PREPARED)
                .build();
        if (paymentService.createPayment(payment)) {
            creatingTransaction(moneyToPay, sourceNumber, destinationCreditCardNumber, payment);
            HttpSession session = request.getSession();
            session.setAttribute("user_credit_cards",
                    creditCardService.getAllCreditCards(((User)session.getAttribute("user")).getId()));
            logger.info("Transaction succeeded");
        }

        return mainPage;
    }

    private void creatingTransaction(double moneyToPay, long sourceNumber, long destinationCreditCardNumber, Payment payment) {
        creditCardService.replenishCreditCard(sourceNumber, moneyToPay * -1);
        creditCardService.replenishCreditCard(destinationCreditCardNumber, moneyToPay);
        payment.setPaymentStatus(PaymentStatus.SENT);
        paymentService.changeStatus(payment);
    }
}
