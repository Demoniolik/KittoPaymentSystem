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

    public CreditCard getCreditCardById(long creditCardId) {
        logger.info("Getting credit card by credit card id");
        return creditCardDao.get(creditCardId);
    }

    public List<CreditCard> getAllCreditCardsThatBelongToUserWithDefaultLimit(long userId) {
        return creditCardDao.getAllCreditCardThatBelongToUserWithDefaultLimit(userId);
    }

    public boolean createCreditCard(CreditCard creditCard) {
        logger.info("Creating new credit card");
        return creditCardDao.save(creditCard) != null && creditCard.getId() != 0;
    }

    public void blockCreditCardById(long cardId) {
        logger.info("Blocking credit card");
        creditCardDao.blockCardById(cardId);
    }

    public int getCountOfCardsThatBelongToUser(long userId) {
        return creditCardDao.getCountOfCardsThatBelongToUSer(userId);
    }

    public List<CreditCard> getAllCreditCardsByCriteriaWithLimit(long userId, String sortingCriteria,
                                                                 String sortingOrder, int page, int pageSize) {
        return creditCardDao.getAllSortedCardsThatBelongToUserWithLimit(userId, sortingCriteria, sortingOrder, page, pageSize);
    }
}
