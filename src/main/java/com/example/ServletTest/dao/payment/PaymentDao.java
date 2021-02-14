package com.example.ServletTest.dao.payment;

import com.example.ServletTest.dao.DAO;
import com.example.ServletTest.model.payment.Payment;

import java.util.List;

public interface PaymentDao extends DAO<Payment> {
    void changeStatus(Payment payment);
    List<Payment> getAllPaymentsByCreditCardNumberId(long cardNumber);
    List<Payment> getAllCardsByCardNumber();

    List<String> getAllCategories();

    List<Payment> getAllPaymentsByCreditCardNumberSortedByCriteria(long currentCreditCard, String sortingCriteria, String sortingOder);

    List<Payment> getAllPaymentsWithLimitOption(long currentCreditCard, int pageSize);

    int getCountOfPaymentsAttachedToCard(long creditCardId);
}
