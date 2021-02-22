package com.example.ServletTest.dao.payment;

import com.example.ServletTest.dao.DAO;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.payment.Payment;

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
