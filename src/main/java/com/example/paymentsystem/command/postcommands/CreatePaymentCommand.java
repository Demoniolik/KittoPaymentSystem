package com.example.paymentsystem.command.postcommands;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.dao.payment.PaymentDaoImpl;
import com.example.paymentsystem.dao.user.UserDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.creditcard.CreditCard;
import com.example.paymentsystem.model.payment.Payment;
import com.example.paymentsystem.model.payment.PaymentBuilder;
import com.example.paymentsystem.model.payment.PaymentCategory;
import com.example.paymentsystem.model.payment.PaymentStatus;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.service.payment.PaymentService;
import com.example.paymentsystem.service.user.UserService;
import com.example.paymentsystem.util.MappingProperties;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This command creates specified payment
 * all the data comes via POST method
 */

public class CreatePaymentCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(CreatePaymentCommand.class);
    private static UserService userService;
    private static PaymentService paymentService;
    private static CreditCardService creditCardService;
    private final String mainPage;
    private final String errorPage;
    private static final Map<String, Long> paymentCategoryToCreditCardNumberAssociation =
            new ConcurrentHashMap<>();

    public CreatePaymentCommand() {
        userService = new UserService(UserDaoImpl.getInstance());
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

        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPagePost");
        errorPage = properties.getProperty("errorPageDatabasePost");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing payment creation command");

        double moneyToPay = Double.parseDouble(request.getParameter("moneyToPay"));
        long sourceNumber = Long.parseLong(request.getParameter("sourceNumber"));
        String categoryOfPayment = request.getParameter("chosenCategory");
        long destinationCreditCardNumber =
                paymentCategoryToCreditCardNumberAssociation.get(categoryOfPayment);

        CreditCard sourceCreditCard;
        CreditCard destinationCreditCard;
        try {
            sourceCreditCard = creditCardService.getCreditCardByNumber(sourceNumber);
            destinationCreditCard = creditCardService.getCreditCardByNumber(destinationCreditCardNumber);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        Payment payment;
        try {
            payment = new PaymentBuilder().setMoney(moneyToPay)
                    .setDescription(userService
                            .getSpecifiedUserNameByCardId(destinationCreditCard.getId()))
                    .setCreditCardIdSource(sourceCreditCard.getId())
                    .setCreditCardIdDestination(destinationCreditCard.getId())
                    .setDate(LocalDateTime.now())
                    .setPaymentStatus(PaymentStatus.PREPARED)
                    .setPaymentCategory(getCategoryFromName(categoryOfPayment))
                    .build();
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }
        if (paymentService.createPayment(payment)) {
            try {
                creatingTransaction(moneyToPay, sourceNumber, destinationCreditCardNumber, payment);
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }

            HttpSession session = request.getSession();

            try {
                session.setAttribute("user_credit_cards",
                        creditCardService.getAllUnblockedCreditCards(((User)session.getAttribute("user")).getId()));
            } catch (DatabaseException e) {
                request.setAttribute("errorCause", e.getMessage());
                return errorPage;
            }
            logger.info("Payment succeeded");
        }

        return mainPage;
    }

    private static PaymentCategory getCategoryFromName(String name) {
        if (PaymentCategory.REPLENISHING_MOBILE_PHONE.getCategory().equals(name)) {
            return PaymentCategory.REPLENISHING_MOBILE_PHONE;
        } else if (PaymentCategory.REQUISITE.getCategory().equals(name)) {
            return PaymentCategory.REQUISITE;
        }else if (PaymentCategory.UTILITIES.getCategory().equals(name)) {
            return PaymentCategory.UTILITIES;
        } else if (PaymentCategory.CHARITY.getCategory().equals(name)) {
            return PaymentCategory.CHARITY;
        }
        return null;
    }

    private void creatingTransaction(double moneyToPay, long sourceNumber, long destinationCreditCardNumber, Payment payment)
            throws DatabaseException {
        creditCardService.replenishCreditCard(sourceNumber, moneyToPay * -1);
        creditCardService.replenishCreditCard(destinationCreditCardNumber, moneyToPay);
        payment.setPaymentStatus(PaymentStatus.SENT);
        paymentService.changeStatus(payment);
    }
}
