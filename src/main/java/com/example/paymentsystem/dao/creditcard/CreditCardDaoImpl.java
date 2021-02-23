package com.example.paymentsystem.dao.creditcard;

import com.example.paymentsystem.connectionpool.BasicConnectionPool;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.creditcard.CreditCard;
import com.example.paymentsystem.model.creditcard.CreditCardBuilder;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditCardDaoImpl implements CreditCardDao {
    private static final Logger logger = Logger.getLogger(CreditCardDaoImpl.class);
    private Connection connection;
    private static CreditCardDaoImpl instance;
    private BasicConnectionPool basicConnectionPool;
    private static final String QUERY_TO_CREATE_NEW_CARD =
            "INSERT INTO credit_card SET " +
                    "number = ?, name = ?, " +
                    "money_on_card = ?, blocked = ?, " +
                    "cvc = ?, user_id = ?";
    private static final String QUERY_TO_GET_CARD_BY_ID = "SELECT * FROM credit_card WHERE id = ?";
    private static final String QUERY_TO_GET_ALL_CARDS_BY_USER_ID =
            "SELECT * FROM credit_card WHERE user_id = ?";
    private static final String QUERY_TO_GET_ALL_UNBLOCKED_CARDS =
            QUERY_TO_GET_ALL_CARDS_BY_USER_ID + " AND blocked = 0";
    private static final String QUERY_TO_UPDATE_MONEY_STATUS_OF_CARD_BY_CARD_NUMBER =
            "UPDATE credit_card SET money_on_card = ? WHERE number = ?";
    private static final String QUERY_TO_GET_CREDIT_CARD_BY_NUMBER =
            "SELECT * FROM credit_card WHERE number = ?";
    private static final String QUERY_TO_CHANGE_BLOCKING_STATUS_OF_CREDIT_CARD_BY_ID =
            "UPDATE credit_card SET blocked = ? WHERE id = ?";
    private static final String QUERY_TO_GET_COUNT_OF_CARD_THAT_BELONG_TO_USER =
            "SELECT COUNT(*) FROM credit_card WHERE user_id = ?";
    private static final String LIMIT_OPTION =
            "LIMIT ? OFFSET ?";
    private static final String QUERY_TO_GET_ALL_BLOCKED_CARDS_BY_USER_ID =
            "SELECT * FROM credit_card WHERE user_id = ? AND blocked = 1";
    private static final String QUERY_TO_BLOCK_ALL_USER_CARDS_BY_USER_ID =
            "UPDATE credit_card SET blocked = 1 WHERE user_id = ?";

    private CreditCardDaoImpl() {
        // TODO: load all the constant queries from the properties file
        try {
            basicConnectionPool = BasicConnectionPool.create();
            connection = basicConnectionPool.getConnection();
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            logger.error(exception);
        }
    }

    public static CreditCardDao getInstance() {
        if (instance == null) {
            instance = new CreditCardDaoImpl();
        }
        return instance;
    }

    @Override
    public CreditCard get(long id) throws DatabaseException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_CARD_BY_ID)) {
            statement.setLong(1, id);
            statement.execute();
            return getCreditCardFromResultSet(statement.getResultSet());
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    @Override
    public List<CreditCard> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public CreditCard save(CreditCard creditCard) throws DatabaseException {
        try (PreparedStatement statement
                     = connection.prepareStatement(QUERY_TO_CREATE_NEW_CARD)) {
            statement.setLong(1, creditCard.getNumber());
            statement.setString(2, creditCard.getName());
            statement.setDouble(3, creditCard.getMoneyOnCard());
            statement.setBoolean(4, creditCard.isBlocked());
            statement.setInt(5, creditCard.getCvcCode());
            statement.setLong(6, creditCard.getUserId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Card wasn't created");
            } else {
                logger.info("Card creation was successful");
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        creditCard.setId(generatedKeys.getLong(1));
                    }else {
                        logger.error("Failed to create card, no obtained id");
                        throw new DatabaseException("Failed to create card, no obtained id");
                    }
                }
            }
        } catch (SQLException | DatabaseException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        return creditCard;
    }

    @Override
    public void update(CreditCard creditCard, String[] params) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(CreditCard creditCard) {
        throw new NotImplementedException();
    }

    @Override
    public CreditCard getCardByNumber(long creditCardNumber) throws DatabaseException {
        logger.info("Getting credit card by number");
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_CREDIT_CARD_BY_NUMBER);) {
            statement.setLong(1, creditCardNumber);
            statement.execute();
            return getCreditCardFromResultSet(statement.getResultSet());
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }


    @Override
    public List<CreditCard> getAllUnblockedCardsOfCurrentUser(long userId) throws DatabaseException {
        logger.info("Retrieving user's credit cards");
        return getCreditCardsByCriteria(userId, QUERY_TO_GET_ALL_UNBLOCKED_CARDS);
    }

    @Override
    public boolean replenishCreditCard(long creditCardNumber, double replenishMoney) throws DatabaseException {
        logger.info("Getting money from card");
        double resultOfReplenishing = getCardByNumber(creditCardNumber).getMoneyOnCard();
        logger.info("Replenishing money on card");
        resultOfReplenishing += replenishMoney;
        try (PreparedStatement statement =
                     connection.prepareStatement(QUERY_TO_UPDATE_MONEY_STATUS_OF_CARD_BY_CARD_NUMBER);) {
            statement.setDouble(1, resultOfReplenishing);
            statement.setLong(2, creditCardNumber);
            statement.executeUpdate();
        } catch (SQLException | NullPointerException exception) {
            logger.error(exception); // Money can just gone from card
            throw new DatabaseException(exception.getMessage());
        }
        return true;
    }

    @Override
    public void changeBlockStatusCardById(long cardId, int option) throws DatabaseException {
        try (PreparedStatement statement
                     = connection.prepareStatement(QUERY_TO_CHANGE_BLOCKING_STATUS_OF_CREDIT_CARD_BY_ID)) {
            statement.setInt(1, option);
            statement.setLong(2, cardId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        logger.info("Card is successfully blocked");
    }

    @Override
    public int getCountOfCardsThatBelongToUser(long userId) throws DatabaseException {
        try (PreparedStatement statement
                     = connection.prepareStatement(QUERY_TO_GET_COUNT_OF_CARD_THAT_BELONG_TO_USER)) {
            statement.setLong(1, userId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        return 0;
    }

    @Override
    public List<CreditCard> getAllSortedCardsThatBelongToUserWithLimit(long userId, String sortingCriteria,
                                                                       String sortingOrder, int page, int pageSize) throws DatabaseException {
        String query = QUERY_TO_GET_ALL_CARDS_BY_USER_ID + " ORDER BY " + sortingCriteria
                + " " + sortingOrder + " " + LIMIT_OPTION;
        return getCreditCardsByCriteriaWithLimit(userId, query, page, pageSize);
    }

    @Override
    public List<CreditCard> getAllCreditCardThatBelongToUserWithDefaultLimit(long userId) throws DatabaseException {
        String query = QUERY_TO_GET_ALL_CARDS_BY_USER_ID + " " + LIMIT_OPTION;
        final int DEFAULT_PAGE_SIZE = 4;
        final int DEFAULT_PAGE = 1;
        return getCreditCardsByCriteriaWithLimit(userId, query,DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    @Override
    public List<CreditCard> getAllBlockedCreditCardsByUserId(long userId) throws DatabaseException {
        return getCreditCardsByCriteria(userId, QUERY_TO_GET_ALL_BLOCKED_CARDS_BY_USER_ID);
    }

    @Override
    public void blockAllUserCards(long userId) throws DatabaseException {
        try (PreparedStatement statement =
                     connection.prepareStatement(QUERY_TO_BLOCK_ALL_USER_CARDS_BY_USER_ID)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    private List<CreditCard> getCreditCardsByCriteria(long userId, String sortingCriteria) throws DatabaseException {
        try (PreparedStatement statement = connection.prepareStatement(sortingCriteria)) {
            statement.setLong(1, userId);
            statement.execute();
            List<CreditCard> creditCards = getCreditCardsFromResultSet(statement.getResultSet());
            System.out.println(creditCards);
            logger.info("Users credit cards are loaded");
            return creditCards;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    private List<CreditCard> getCreditCardsByCriteriaWithLimit(long userId, String sortingCriteria, int page, int pageSize)
            throws DatabaseException {
        try (PreparedStatement statement = connection.prepareStatement(sortingCriteria)) {
            statement.setLong(1, userId);
            statement.setInt(2, pageSize);
            statement.setInt(3, pageSize * (page - 1));
            statement.execute();
            List<CreditCard> creditCards = getCreditCardsFromResultSet(statement.getResultSet());
            System.out.println(creditCards);
            logger.info("Users credit cards are loaded");
            return creditCards;
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    private CreditCard getCreditCardFromResultSet(ResultSet resultSet) throws DatabaseException {
        logger.info("Getting card from result set");
        try {
            if (resultSet.next()) {
                return buildCreditCardFromResultSet(resultSet);
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        logger.info("No credit card with this name was found");
        return null;
    }

    private CreditCard buildCreditCardFromResultSet(ResultSet resultSet) throws SQLException {
        logger.info("Building credit card object");
        return new CreditCardBuilder()
                .setId(resultSet.getInt("id"))
                .setNumber(resultSet.getLong("number"))
                .setName(resultSet.getString("name"))
                .setMoneyOnCard(resultSet.getDouble("money_on_card"))
                .setCvcCode(resultSet.getInt("cvc"))
                .setBlocked(resultSet.getBoolean("blocked"))
                .build();
    }

    private List<CreditCard> getCreditCardsFromResultSet(ResultSet resultSet) throws DatabaseException {
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            while (resultSet.next()) {
                creditCards.add(buildCreditCardFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        return creditCards;
    }
}
