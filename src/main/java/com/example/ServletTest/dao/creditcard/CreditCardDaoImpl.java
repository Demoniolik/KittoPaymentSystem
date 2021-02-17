package com.example.ServletTest.dao.creditcard;

import com.example.ServletTest.connectionpool.BasicConnectionPool;
import com.example.ServletTest.model.creditcard.CreditCard;
import com.example.ServletTest.model.creditcard.CreditCardBuilder;
import org.apache.log4j.Logger;

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
                    "money_on_card = ?, blocked = ? " +
                    "cvc = ?, user_id = ?";
    private static final String QUERY_TO_GET_CARD_BY_ID = "SELECT * FROM credit_card WHERE id = ?";
    private static final String QUERY_TO_GET_ALL_CARDS_BY_USER_ID =
            "SELECT * FROM credit_card WHERE user_id = ?";
    private static final String QUERY_TO_UPDATE_MONEY_STATUS_OF_CARD_BY_CARD_NUMBER =
            "UPDATE credit_card SET money_on_card = ? WHERE number = ?";
    private static final String QUERY_TO_GET_CREDIT_CARD_BY_NUMBER =
            "SELECT * FROM credit_card WHERE number = ?";
    private static final String QUERY_TO_BLOCK_CREDIT_CARD_BY_ID =
            "UPDATE credit_card SET blocked = 1 WHERE id = ?";
    private static final String QUERY_TO_GET_COUNT_OF_CARD_THAT_BELONG_TO_USER =
            "SELECT COUNT(*) FROM credit_card WHERE user_id = ?";
    private static final String LIMIT_OPTION =
            "LIMIT ? OFFSET ?";
    private static final String QUERY_TO_GET_ALL_BLOCKED_CARDS_BY_USER_ID =
            "SELECT * FROM credit_card WHERE user_id = ? AND blocked = 1";

    private CreditCardDaoImpl() {
        // TODO: load all the constant queries from the properties file
        try {
            basicConnectionPool = BasicConnectionPool.create();
            connection = basicConnectionPool.getConnection();
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            // TODO: throw database exception
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
    public CreditCard get(long id) {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_CARD_BY_ID)) {
            statement.setLong(1, id);
            statement.execute();
            return getCreditCardFromResultSet(statement.getResultSet());
        } catch (SQLException exception) {
            //TODO: create database exception
            logger.error(exception);
        }
        return null;
    }

    @Override
    public List<CreditCard> getAll() {

        return null;
    }

    @Override
    public CreditCard save(CreditCard creditCard) {
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
                        // TODO: here you need to throw database exception
                    }
                }
            }
        } catch (SQLException exception) {
            // TODO: throw new database exception
            logger.error(exception);
        }
        return creditCard;
    }

    @Override
    public void update(CreditCard creditCard, String[] params) {

    }

    @Override
    public void delete(CreditCard creditCard) {

    }

    @Override
    public CreditCard getCardByNumber(long creditCardNumber) {
        logger.info("Getting credit card by number");
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_CREDIT_CARD_BY_NUMBER);) {
            statement.setLong(1, creditCardNumber);
            statement.execute();
            return getCreditCardFromResultSet(statement.getResultSet());
        } catch (SQLException exception) {
            // TODO: throw new database exception
            logger.error(exception);
        }
        return null;
    }

    @Override
    public CreditCard getCardByName() {
        return null;
    }

    @Override
    public List<CreditCard> getAllCardsByName() {
        return null;
    }

    @Override
    public List<CreditCard> getAllCardsByNumber() {
        return null;
    }

    @Override
    public List<CreditCard> getAllCardsByMoneyOnAccount() {
        return null;
    }

    @Override
    public List<CreditCard> getAllCardOfCurrentUser(long userId) {
        logger.info("Retrieving user's credit cards");
        return getCreditCardsByCriteria(userId, QUERY_TO_GET_ALL_CARDS_BY_USER_ID);
    }

    @Override
    public boolean replenishCreditCard(long creditCardNumber, double replenishMoney) {
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
            // TODO: throw new database exception
            logger.error(exception); // Money can just gone from card
            return false;
        }
        return true;
    }

    @Override
    public void blockCardById(long cardId) {
        try (PreparedStatement statement
                     = connection.prepareStatement(QUERY_TO_BLOCK_CREDIT_CARD_BY_ID)) {
            statement.setLong(1, cardId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            //TODO: Create database exception
            logger.error(exception);
        }
        logger.info("Card is successfully blocked");
    }

    @Override
    public int getCountOfCardsThatBelongToUser(long userId) {
        try (PreparedStatement statement
                     = connection.prepareStatement(QUERY_TO_GET_COUNT_OF_CARD_THAT_BELONG_TO_USER)) {
            statement.setLong(1, userId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            //TODO: Create database exception
            logger.error(exception);
        }
        return 0;
    }

    @Override
    public List<CreditCard> getAllSortedCardsThatBelongToUserWithLimit(long userId, String sortingCriteria,
                                                                       String sortingOrder, int page, int pageSize) {
        String query = QUERY_TO_GET_ALL_CARDS_BY_USER_ID + " ORDER BY " + sortingCriteria
                + " " + sortingOrder + " " + LIMIT_OPTION;
        return getCreditCardsByCriteriaWithLimit(userId, query, page, pageSize);
    }

    @Override
    public List<CreditCard> getAllCreditCardThatBelongToUserWithDefaultLimit(long userId) {
        String query = QUERY_TO_GET_ALL_CARDS_BY_USER_ID + " " + LIMIT_OPTION;
        final int DEFAULT_PAGE_SIZE = 4;
        final int DEFAULT_PAGE = 1;
        return getCreditCardsByCriteriaWithLimit(userId, query,DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    @Override
    public List<CreditCard> getAllBlockedCreditCardsByUserId(long userId) {
        return getCreditCardsByCriteria(userId, QUERY_TO_GET_ALL_BLOCKED_CARDS_BY_USER_ID);
    }

    private List<CreditCard> getCreditCardsByCriteria(long userId, String sortingCriteria) {
        try (PreparedStatement statement = connection.prepareStatement(sortingCriteria)) {
            statement.setLong(1, userId);
            statement.execute();
            List<CreditCard> creditCards = getCreditCardsFromResultSet(statement.getResultSet());
            System.out.println(creditCards);
            logger.info("Users credit cards are loaded");
            return creditCards;
        } catch (SQLException exception) {
            //TODO: throw new database exception
            logger.error(exception);
        }
        return null;
    }

    private List<CreditCard> getCreditCardsByCriteriaWithLimit(long userId, String sortingCriteria, int page, int pageSize) {
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
            //TODO: throw new database exception
            logger.error(exception);
        }
        return null;
    }

    private CreditCard getCreditCardFromResultSet(ResultSet resultSet) {
        logger.info("Getting card from result set");
        try {
            if (resultSet.next()) {
                return buildCreditCardFromResultSet(resultSet);
            }
        } catch (SQLException exception) {
            // TODO: throw database exception
            logger.error(exception);
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

    private List<CreditCard> getCreditCardsFromResultSet(ResultSet resultSet) {
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            while (resultSet.next()) {
                creditCards.add(buildCreditCardFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            //TODO: here you need to add database exception
            logger.error(exception);
        }
        return creditCards;
    }
}
