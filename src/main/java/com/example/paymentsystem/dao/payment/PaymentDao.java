package com.example.paymentsystem.dao.payment;

import com.example.paymentsystem.dao.DAO;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.payment.Payment;

import java.util.List;

public interface PaymentDao extends DAO<Payment> {
    void changeStatus(Payment payment) throws DatabaseException;
    List<Payment> getAllPaymentsByCreditCardNumberId(long cardNumber) throws DatabaseException;
    List<Payment> getAllCardsByCardNumber();

    List<String> getAllCategories() throws DatabaseException;

    List<Payment> getAllPaymentsByCreditCardNumberSortedByCriteria(long currentCreditCard, String sortingCriteria, String sortingOder) throws DatabaseException;

    List<Payment> getAllPaymentsWithLimitOption(long currentCreditCard, int pageSize) throws DatabaseException;

    List<Payment> getAllPaymentsSortedWithLimitOption(long currentCreditCard, int pageSize,
                                                             String sortingCriteria, String sortingOrder) throws DatabaseException;

    int getCountOfPaymentsAttachedToCard(long creditCardId) throws DatabaseException;
}
