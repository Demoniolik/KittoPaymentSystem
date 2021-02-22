package com.example.ServletTest.service.payment;

import com.example.ServletTest.dao.payment.PaymentDao;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.payment.Payment;
import org.apache.log4j.Logger;

import java.util.List;

public class PaymentService {
    private static final Logger logger = Logger.getLogger(PaymentService.class);
    private PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public boolean createPayment(Payment payment) {
        logger.info("Creating new payment");
        try {
            return payment != null && paymentDao.save(payment).getId() != 0;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void changeStatus(Payment payment) throws DatabaseException {
        logger.info("Changing status of payment");
        paymentDao.changeStatus(payment);
    }

    public List<Payment> getListOfPaymentsThatBelongToCreditCard(long currentCreditCard) throws DatabaseException {
        logger.info("Retrieving all payments that belong to credit card");
        return paymentDao.getAllPaymentsByCreditCardNumberId(currentCreditCard);
    }

    public List<String> getAllCategories() throws DatabaseException {
        logger.info("Getting all categories");
        return paymentDao.getAllCategories();
    }

    public List<Payment> getListOfPaymentsSortedByCriteria(long currentCreditCard, String sortingCriteria, String sortingOrder) throws DatabaseException {
        logger.info("Retrieving and sorting payments that belong to credit card");
        return paymentDao.getAllPaymentsByCreditCardNumberSortedByCriteria(currentCreditCard, sortingCriteria, sortingOrder);
    }

    public List<Payment> getAllPaymentsWithLimitOption(long currentCreditCard, int pageSize) throws DatabaseException {
        logger.info("Getting all payments with limit option");
        return paymentDao.getAllPaymentsWithLimitOption(currentCreditCard ,pageSize);
    }

    public List<Payment> getAllPaymentsSortedWithLimitOption(long currentCreditCard, int pageSize,
                                                             String sortingCriteria, String sortingOrder) throws DatabaseException {
        logger.info("Getting all payments with limit option and sorting criteria plus order");
        return paymentDao.getAllPaymentsSortedWithLimitOption(currentCreditCard, pageSize, sortingCriteria, sortingOrder);
    }

    public int getAmountOfCardPayments(long creditCardId) throws DatabaseException {
        logger.info("Getting amount of payments that belong to card");
        return paymentDao.getCountOfPaymentsAttachedToCard(creditCardId);
    }
}
