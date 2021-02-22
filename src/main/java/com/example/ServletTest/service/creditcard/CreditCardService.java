package com.example.ServletTest.service.creditcard;

import com.example.ServletTest.dao.creditcard.CreditCardDao;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.creditcard.CreditCard;
import org.apache.log4j.Logger;

import java.util.List;

public class CreditCardService {
    private static final Logger logger = Logger.getLogger(CreditCardService.class);
    private CreditCardDao creditCardDao;

    public CreditCardService(CreditCardDao creditCardDao) {
        this.creditCardDao = creditCardDao;
    }

    public List<CreditCard> getAllUnblockedCreditCards(long userId)
            throws DatabaseException {
        logger.info("Getting all unblocked cards");
        return creditCardDao.getAllUnblockedCardsOfCurrentUser(userId);
    }

    public boolean replenishCreditCard(long creditCardNumber, double replenishMoney)
            throws DatabaseException {
        logger.info("Replenishing credit card");
        return creditCardDao.replenishCreditCard(creditCardNumber, replenishMoney);
    }

    public CreditCard getCreditCardByNumber(long cardNumber) throws DatabaseException {
        logger.info("Getting card that corresponds to its number");
        return creditCardDao.getCardByNumber(cardNumber);
    }

    public CreditCard getCreditCardById(long creditCardId) throws DatabaseException {
        logger.info("Getting credit card by credit card id");
        return creditCardDao.get(creditCardId);
    }

    public List<CreditCard> getAllCreditCardsThatBelongToUserWithDefaultLimit(long userId)
            throws DatabaseException {
        logger.info("Getting all cards that belong to user with default limit");
        return creditCardDao.getAllCreditCardThatBelongToUserWithDefaultLimit(userId);
    }

    public boolean createCreditCard(CreditCard creditCard) throws DatabaseException {
        logger.info("Creating new credit card");
        return creditCardDao.save(creditCard) != null && creditCard.getId() != 0;
    }

    public void changeBlockingStatusCreditCardById(long cardId, int option) throws DatabaseException {
        logger.info("changing blocking status of credit card");
        creditCardDao.changeBlockStatusCardById(cardId, option);
    }

    public int getCountOfCardsThatBelongToUser(long userId) throws DatabaseException {
        logger.info("Getting all cards that belong to user");
        return creditCardDao.getCountOfCardsThatBelongToUser(userId);
    }

    public List<CreditCard> getAllCreditCardsByCriteriaWithLimit(long userId, String sortingCriteria,
                                                                 String sortingOrder, int page, int pageSize) throws DatabaseException {
        logger.info("Retrieving all credit cards with sorting criteria + pagination");
        return creditCardDao.getAllSortedCardsThatBelongToUserWithLimit(userId, sortingCriteria, sortingOrder, page, pageSize);
    }

    public List<CreditCard> getAllBlockedCreditCardsThatBelongToUser(long userId) throws DatabaseException {
        logger.info("Retrieving all blocked credit cards that belong to user");
        return creditCardDao.getAllBlockedCreditCardsByUserId(userId);
    }

    public void blockAllUserCards(long id) throws DatabaseException {
        logger.info("Blocking all user cards");
        creditCardDao.blockAllUserCards(id);
    }
}
