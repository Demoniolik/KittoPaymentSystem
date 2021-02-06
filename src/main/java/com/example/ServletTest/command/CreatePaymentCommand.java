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

public class CreatePaymentCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CreatePaymentCommand.class);
    private PaymentService paymentService;
    private CreditCardService creditCardService;
    private String mainPage;

    public CreatePaymentCommand() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        // TODO: set page from properties file
        mainPage = "WEB-INF/MainContent.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing payment creation command");
        double moneyToPay = Double.parseDouble(request.getParameter("moneyToPay"));
        long sourceNumber = Long.parseLong(request.getParameter("sourceNumber"));
        long destinationNumber = Long.parseLong(request.getParameter("destinationNumber"));
        CreditCard sourceCreditCard = creditCardService.getCreditCardByNumber(sourceNumber);
        CreditCard destinationCreditCard = creditCardService.getCreditCardByNumber(destinationNumber);
        Payment payment = new PaymentBuilder().setMoney(moneyToPay)
                .setCreditCardIdSource(sourceCreditCard.getId())
                .setCreditCardIdDestination(destinationCreditCard.getId())
                .setDate(LocalDateTime.now())
                .setPaymentStatus(PaymentStatus.PREPARED)
                .build();
        if (paymentService.createPayment(payment)) {
            creditCardService.replenishCreditCard(sourceNumber, moneyToPay * -1);
            creditCardService.replenishCreditCard(destinationNumber, moneyToPay);
            // TODO: here you need to go to db and put SENT status
            payment.setPaymentStatus(PaymentStatus.SENT);
            paymentService.changeStatus(payment);
            HttpSession session = request.getSession();
            session.setAttribute("user_credit_cards",
                    creditCardService.getAllCreditCards(((User)session.getAttribute("user")).getId()));
            logger.info("Transaction succeeded");
        }

        return mainPage;
    }
}
