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
    private static CreditCardDaoImpl instance;
    private BasicConnectionPool basicConnectionPool;
    private static final String QUERY_TO_GET_CARD_BY_ID = "SELECT * FROM credit_card WHERE id = ?";
    private static final String QUERY_TO_GET_ALL_CARDS_BY_USER_ID =
            "SELECT * FROM credit_card WHERE user_id = ?";
    private static final String QUERY_TO_UPDATE_MONEY_STATUS_OF_CARD_BY_CARD_NUMBER =
            "UPDATE credit_card SET money_on_card = ? WHERE number = ?";
    private static final String QUERY_TO_GET_CREDIT_CARD_BY_NUMBER =
            "SELECT * FROM credit_card WHERE number = ?";

    private CreditCardDaoImpl() {
        // TODO: load all the constant queries from the properties file
        try {
            basicConnectionPool = BasicConnectionPool.create();
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            // TODO: throw database exception
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
        try (Connection connection = basicConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_CARD_BY_ID)) {
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
        return null;
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
        try {
            Connection connection = basicConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_CREDIT_CARD_BY_NUMBER);
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
        logger.info("Retriving user's credit cards");
        try {
            Connection connection = basicConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_ALL_CARDS_BY_USER_ID);
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

    @Override
    public boolean replenishCreditCard(long creditCardNumber, double replenishMoney) {
        logger.info("Getting money from card");
        double resultOfReplenishing = getCardByNumber(creditCardNumber).getMoneyOnCard();
        logger.info("Replenishing money on card");
        resultOfReplenishing += replenishMoney;
        try {
            Connection connection = basicConnectionPool.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(QUERY_TO_UPDATE_MONEY_STATUS_OF_CARD_BY_CARD_NUMBER);
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
                .setId(resultSet.getLong("id"))
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
