package com.example.ServletTest.service.payment;

import com.example.ServletTest.dao.payment.PaymentDao;
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
        return payment != null && paymentDao.save(payment).getId() != 0;
    }

    public void changeStatus(Payment payment) {
        paymentDao.changeStatus(payment);
    }

    public List<Payment> getListOfPaymentsThatBelongToCreditCard(long currentCreditCard) {
        return paymentDao.getAllPaymentsByUserId(currentCreditCard);
    }

    public List<String> getAllCategories() {
        logger.info("Getting all categories");
        return paymentDao.getAllCategories();
    }

}
