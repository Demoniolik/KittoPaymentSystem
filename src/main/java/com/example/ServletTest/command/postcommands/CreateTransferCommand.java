package com.example.ServletTest.command.postcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.payment.Payment;
import com.example.ServletTest.model.payment.PaymentBuilder;
import com.example.ServletTest.model.payment.PaymentCategory;
import com.example.ServletTest.model.payment.PaymentStatus;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.payment.PaymentService;
import com.example.ServletTest.service.user.UserService;
import com.example.ServletTest.util.MappingProperties;
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
