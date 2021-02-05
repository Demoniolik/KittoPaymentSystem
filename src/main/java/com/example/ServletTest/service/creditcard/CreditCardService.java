package com.example.ServletTest.service.creditcard;

import com.example.ServletTest.dao.creditcard.CreditCardDao;
import com.example.ServletTest.model.creditcard.CreditCard;
import org.apache.log4j.Logger;

import java.util.List;

public class CreditCardService {
    private static final Logger logger = Logger.getLogger(CreditCardService.class);
    private CreditCardDao creditCardDao;

    public CreditCardService(CreditCardDao creditCardDao) {
        this.creditCardDao = creditCardDao;
    }

    public List<CreditCard> getAllCreditCards(long userId) {
        return creditCardDao.getAllCardOfCurrentUser(userId);
    }

    public boolean replenishCreditCard(long creditCardNumber, double replenishMoney) {
        // TODO: fix moment when return value is null
        return creditCardDao.replenishCreditCard(creditCardNumber, replenishMoney);
    }

    public CreditCard getCreditCardByNumber(long cardNumber) {
        return creditCardDao.getCardByNumber(cardNumber);
    }
}
