package com.example.ServletTest.dao.payment;

import com.example.ServletTest.dao.DAO;
import com.example.ServletTest.model.payment.Payment;

import java.util.List;

public interface PaymentDao extends DAO<Payment> {
    void changeStatus(Payment payment);
    List<Payment> getAllPaymentsByCreditCardNumber(long cardNumber);
    List<Payment> getAllCardsByCardNumber();

    List<String> getAllCategories();
}
