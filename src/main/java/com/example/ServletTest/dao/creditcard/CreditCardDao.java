package com.example.ServletTest.dao.creditcard;

import com.example.ServletTest.dao.DAO;
import com.example.ServletTest.model.creditcard.CreditCard;

import java.util.List;

public interface CreditCardDao extends DAO<CreditCard> {
    CreditCard getCardByNumber();
    CreditCard getCardByName();
    List<CreditCard> getAllCardsByName();
    List<CreditCard> getAllCardsByNumber();
    List<CreditCard> getAllCardsByMoneyOnAccount();
    List<CreditCard> getAllCardOfCurrentUser(long userId);
}
