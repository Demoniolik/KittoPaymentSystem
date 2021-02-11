package com.example.ServletTest.command;

import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.payment.Payment;
import com.example.ServletTest.model.payment.PaymentBuilder;
import com.example.ServletTest.model.payment.PaymentCategory;
import com.example.ServletTest.model.payment.PaymentStatus;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.payment.PaymentService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

public class CreateTransferCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CreateTransferCommand.class);
    private PaymentService paymentService;
    private CreditCardService creditCardService;
    private String mainPage;

    public CreateTransferCommand() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        // TODO: set page from properties file
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing transfer creation command");

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
                .setPaymentCategory(PaymentCategory.TRANSFER_TO_CARD)
                .build();

        if (paymentService.createPayment(payment)) {
            creditCardService.replenishCreditCard(sourceNumber, moneyToPay * -1);
            creditCardService.replenishCreditCard(destinationNumber, moneyToPay);
            payment.setPaymentStatus(PaymentStatus.SENT);
            paymentService.changeStatus(payment);
            HttpSession session = request.getSession();
            List<CreditCard> creditCards = creditCardService.getAllCreditCards(((User)session.getAttribute("user")).getId());
            session.setAttribute("user_credit_cards", creditCards);
            List<Payment> payments = paymentService
                    .getListOfPaymentsThatBelongToCreditCard(creditCards.get(0).getId());
            session.setAttribute("creditCardPayments", payments);
            logger.info("Transaction succeeded");
        }

        return mainPage;
    }
}
