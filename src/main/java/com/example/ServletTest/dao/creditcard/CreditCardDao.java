package com.example.ServletTest.dao.creditcard;

import com.example.ServletTest.dao.DAO;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.creditcard.CreditCard;

import java.util.List;

public interface CreditCardDao extends DAO<CreditCard> {
    CreditCard getCardByNumber(long creditCardNumber) throws DatabaseException;

    List<CreditCard> getAllUnblockedCardsOfCurrentUser(long userId) throws DatabaseException;

    boolean replenishCreditCard(long creditCardNumber, double replenishMoney) throws DatabaseException;

    void changeBlockStatusCardById(long cardId, int option) throws DatabaseException;

    int getCountOfCardsThatBelongToUser(long userId) throws DatabaseException;

    List<CreditCard> getAllSortedCardsThatBelongToUserWithLimit(long userId, String sortingCriteria,
                                                                String sortingOrder, int page, int pageSize) throws DatabaseException;
    List<CreditCard> getAllCreditCardThatBelongToUserWithDefaultLimit(long userId) throws DatabaseException;

    List<CreditCard> getAllBlockedCreditCardsByUserId(long userId) throws DatabaseException;

    void blockAllUserCards(long id) throws DatabaseException;
}
