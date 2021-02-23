package com.example.paymentsystem.view;

import com.example.paymentsystem.dao.user.UserDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.payment.Payment;
import com.example.paymentsystem.model.payment.PaymentCategory;
import com.example.paymentsystem.model.payment.PaymentStatus;
import com.example.paymentsystem.service.creditcard.CreditCardService;
import com.example.paymentsystem.service.user.UserService;
import com.example.paymentsystem.dao.creditcard.CreditCardDaoImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentWrapper {
    private long id;
    private double money;
    private String description;
    private PaymentStatus paymentStatus;
    private String date;
    private String creditCardDestination;
    private String paymentCategory;
    private String categoryImage;
    private CreditCardService creditCardService;
    private UserService userService;

    public PaymentWrapper(Payment payment) throws DatabaseException {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        userService = new UserService(UserDaoImpl.getInstance());

        this.id = payment.getId();
        this.money = payment.getMoney();
        if (payment.getMoney() < 0) {
            this.description = userService.getSpecifiedUserNameByCardId(payment.getCreditCardIdDestination());
            this.setCreditCardDestination(creditCardService
                    .getCreditCardById(payment.getCreditCardIdDestination()).getNumber());
        } else {
            this.description = userService.getSpecifiedUserNameByCardId(payment.getCreditCardIdSource());
            this.setCreditCardDestination(creditCardService
                    .getCreditCardById(payment.getCreditCardIdSource()).getNumber());
        }
        this.paymentStatus = payment.getPaymentStatus();
        this.setDate(payment.getDate());
        this.paymentCategory = payment.getPaymentCategory().getCategory();
        this.setCategoryImage(payment.getPaymentCategory());
    }


    public long getId() {
        return id;
    }

    public double getMoney() {
        return money;
    }

    public String getDescription() {
        return description;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getDate() {
        return date;
    }

    public String getCreditCardDestination() {
        return creditCardDestination;
    }

    public String getPaymentCategory() {
        return paymentCategory;
    }

    private void setCreditCardDestination(Long creditCardDestination) {
        String creditCardAsString = creditCardDestination.toString();
        this.creditCardDestination = creditCardAsString
                .substring(creditCardAsString.length() - 4);
    }

    private void setDate(LocalDateTime date) {
        this.date = date.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    private void setCategoryImage(PaymentCategory paymentCategory) {
        switch (paymentCategory) {
            case REPLENISHING_MOBILE_PHONE:
                this.categoryImage = "resources/img/content/category/mobile.svg";
                break;
            case REQUISITE:
                this.categoryImage = "resources/img/content/category/requisite.svg";
                break;
            case UTILITIES:
                this.categoryImage = "resources/img/content/category/utilities.svg";
                break;
            case CHARITY:
                this.categoryImage = "resources/img/content/category/charity.svg";
                break;
            case TRANSFER_TO_CARD:
                this.categoryImage = "resources/img/content/category/transfer.svg";
                break;
        }
    }

    public String getCategoryImage() {
        return categoryImage;
    }
}
