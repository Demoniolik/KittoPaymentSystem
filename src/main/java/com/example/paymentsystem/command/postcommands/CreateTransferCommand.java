package com.example.paymentsystem.command.postcommands;

import com.example.paymentsystem.dao.user.UserDaoImpl;
import com.example.paymentsystem.model.payment.PaymentBuilder;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import com.example.paymentsystem.dao.payment.PaymentDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.creditcard.CreditCard;
import com.example.paymentsystem.model.payment.Payment;
import com.example.paymentsystem.model.payment.PaymentCategory;
import com.example.paymentsystem.model.payment.PaymentStatus;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.service.payment.PaymentService;
import com.example.paymentsystem.service.user.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

public class CreateTransferCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CreateTransferCommand.class);
    private final UserService userService;
    private final PaymentService paymentService;
    private final CreditCardService creditCardService;
    private final String mainPage;
    private final String errorPageCardDoesNotExist;
    private final String errorPageCardIsBlocked;
    private final String errorPageDatabase;

    public CreateTransferCommand() {
        userService = new UserService(UserDaoImpl.getInstance());
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPagePost");
        errorPageCardDoesNotExist = properties.getProperty("errorPageDestinationCardIsNotFoundPost");
        errorPageCardIsBlocked = properties.getProperty("errorPageDestinationCardIsBlockedPost");
        errorPageDatabase = properties.getProperty("errorPageDatabasePost");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing transfer creation command");

        double moneyToPay = Double.parseDouble(request.getParameter("moneyToPay"));
        long sourceNumber = Long.parseLong(request.getParameter("chosenCreditCard"));
        long destinationNumber = Long.parseLong(request.getParameter("destinationNumber"));

        CreditCard sourceCreditCard;
        CreditCard destinationCreditCard;
        try {
            sourceCreditCard = creditCardService.getCreditCardByNumber(sourceNumber);
            destinationCreditCard = creditCardService.getCreditCardByNumber(destinationNumber);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPageDatabase;
        }

        if (destinationCreditCard == null) {
            logger.error("Destination card was not found");
            return errorPageCardDoesNotExist;
        } else if (destinationCreditCard.isBlocked()) {
            logger.error("destination card is blocked");
            return errorPageCardIsBlocked;
        }

        Payment payment;
        try {
            payment = new PaymentBuilder().setMoney(moneyToPay)
                    .setDescription(userService.
                            getSpecifiedUserNameByCardId(destinationCreditCard.getId()))
                    .setCreditCardIdSource(sourceCreditCard.getId())
                    .setCreditCardIdDestination(destinationCreditCard.getId())
                    .setDate(LocalDateTime.now())
                    .setPaymentStatus(PaymentStatus.PREPARED)
                    .setPaymentCategory(PaymentCategory.TRANSFER_TO_CARD)
                    .build();
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPageDatabase;
        }

        if (paymentService.createPayment(payment)) {
            try {
                creditCardService.replenishCreditCard(sourceNumber, moneyToPay * -1);
                creditCardService.replenishCreditCard(destinationNumber, moneyToPay);
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPageDatabase;
            }

            payment.setPaymentStatus(PaymentStatus.SENT);
            try {
                paymentService.changeStatus(payment);
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPageDatabase;
            }

            HttpSession session = request.getSession();
            List<CreditCard> creditCards;

            try {
                creditCards = creditCardService
                        .getAllUnblockedCreditCards(((User)session.getAttribute("user")).getId());
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPageDatabase;
            }

            session.setAttribute("user_credit_cards", creditCards);
            logger.info("Transaction succeeded");
        }

        return mainPage;
    }
}
