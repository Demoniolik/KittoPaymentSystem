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
    private static final String QUERY_TO_GET_CARD_BY_ID = "";
    private static final String QUERY_TO_GET_ALL_CARDS_BY_USER_ID
            = "SELECT * FROM credit_card WHERE user_id1 = ?";

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
        if (instance != null) {
            return instance;
        }
        instance = new CreditCardDaoImpl();
        return instance;
    }

    @Override
    public CreditCard get(long id) {
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
    public CreditCard getCardByNumber() {
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

    private List<CreditCard> getCreditCardsFromResultSet(ResultSet resultSet) {
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            while (resultSet.next()) {
                creditCards.add(new CreditCardBuilder()
                        .setId(resultSet.getLong("id"))
                        .setNumber(resultSet.getLong("number"))
                        .setName(resultSet.getString("name"))
                        .setMoneyOnCard(resultSet.getDouble("money_on_card"))
                        .setBlocked(resultSet.getBoolean("blocked"))
                        .build());
            }
        } catch (SQLException exception) {
            //TODO: here you need to add database exception
            logger.error(exception);
        }
        return creditCards;
    }
}
