package com.example.paymentsystem.dao.payment;

import com.example.paymentsystem.connectionpool.BasicConnectionPool;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.payment.Payment;
import com.example.paymentsystem.model.payment.PaymentBuilder;
import com.example.paymentsystem.model.payment.PaymentStatus;
import com.example.paymentsystem.model.payment.PaymentCategory;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    private static final Logger logger = Logger.getLogger(PaymentDaoImpl.class);
    private Connection connection;
    private BasicConnectionPool basicConnectionPool;
    private static PaymentDaoImpl instance;
    private static final String QUERY_TO_CREATE_PAYMENT = "INSERT INTO payment SET money = ?, " +
            "description = ?, status = ?, date = ?, " +
            "credit_card_id_source = ?, credit_card_id_destination = ?, " +
            "category_id = ?";
    private static final String QUERY_TO_CHANGE_STATUS =
            "UPDATE payment SET status = ? WHERE id = ?";
    private static final String QUERY_TO_GET_ALL_PAYMENTS_BY_CARD_NUMBER_ID =
            "SELECT * FROM payment " +
                    "WHERE credit_card_id_source = ? " +
                    "OR credit_card_id_destination = ? ";
    private static final String COUNT_OF_PAYMENTS_ATTACHED_TO_CARD_NUMBER_ID =
            "SELECT COUNT(*) FROM payment " +
                    "WHERE credit_card_id_source = ? " +
                    "OR credit_card_id_destination = ? ";
    private static final String LIMIT_OPTION = " LIMIT ?";
    private static final String DEFAULT_QUERY_TO_GET_ALL_PAYMENTS_BY_CARD_NUMBER_ID
            = QUERY_TO_GET_ALL_PAYMENTS_BY_CARD_NUMBER_ID + " ORDER BY date DESC" + LIMIT_OPTION;
    private static final String QUERY_TO_GET_ALL_CATEGORIES =
            "SELECT * FROM category";

    private PaymentDaoImpl() {
        try {
            basicConnectionPool = BasicConnectionPool.create();
            connection = basicConnectionPool.getConnection();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            logger.error(exception);
        }
    }

    public static PaymentDaoImpl getInstance() {
        if (instance == null){
            instance = new PaymentDaoImpl();
        }
        return instance;
    }

    @Override
    public Payment get(long id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Payment> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public Payment save(Payment payment) throws DatabaseException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_CREATE_PAYMENT,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, payment.getMoney());
            statement.setString(2, payment.getDescription());
            statement.setLong(3, payment.getPaymentStatus().ordinal() + 1);
            statement.setString(4, payment.getDate().toString());
            statement.setLong(5, payment.getCreditCardIdSource());
            statement.setLong(6, payment.getCreditCardIdDestination());
            statement.setLong(7, payment.getPaymentCategory().ordinal() + 1);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Payment wasn't created");
            } else {
                logger.info("Payment creation was successful");
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        payment.setId(generatedKeys.getLong(1));
                    }else {
                        logger.error("Failed to create payment, no obtained id");
                        throw new DatabaseException("Failed to create payments, no obtained id");
                    }
                }
            }
        } catch (SQLException | DatabaseException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        return payment;
    }

    @Override
    public void update(Payment payment, String[] params) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Payment payment) {
        throw new NotImplementedException();
    }

    @Override
    public void changeStatus(Payment payment) throws DatabaseException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_CHANGE_STATUS)) {
            statement.setLong(1, payment.getPaymentStatus().ordinal() + 1);
            statement.setLong(2, payment.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    @Override
    public List<Payment> getAllPaymentsByCreditCardNumberSortedByCriteria
            (long currentCreditCard, String sortingCriteria, String sortingOrder) throws DatabaseException {
        String query = QUERY_TO_GET_ALL_PAYMENTS_BY_CARD_NUMBER_ID
                + " ORDER BY " + sortingCriteria + " " + sortingOrder + LIMIT_OPTION;
        final int LIMIT = 10;
        return getPayments(currentCreditCard, query, LIMIT);
    }

    public List<Payment> getAllPaymentsByCreditCardNumberId(long currentCreditCard) throws DatabaseException {
        final int LIMIT = 10;
        return getPayments(currentCreditCard, DEFAULT_QUERY_TO_GET_ALL_PAYMENTS_BY_CARD_NUMBER_ID, LIMIT);
    }

    @Override
    public List<Payment> getAllPaymentsWithLimitOption(long currentCreditCard, int pageSize) throws DatabaseException {
        return getPayments(currentCreditCard, DEFAULT_QUERY_TO_GET_ALL_PAYMENTS_BY_CARD_NUMBER_ID, pageSize);
    }

    @Override
    public List<Payment> getAllPaymentsSortedWithLimitOption(long currentCreditCard, int pageSize,
                                                       String sortingCriteria, String sortingOrder) throws DatabaseException {
        String query = QUERY_TO_GET_ALL_PAYMENTS_BY_CARD_NUMBER_ID + "ORDER BY " + sortingCriteria + " " +
                sortingOrder + LIMIT_OPTION;
        return getPayments(currentCreditCard, query, pageSize);
    }

    @Override
    public int getCountOfPaymentsAttachedToCard(long currentCreditCard) throws DatabaseException {
        try (PreparedStatement statement =
                     connection.prepareStatement(COUNT_OF_PAYMENTS_ATTACHED_TO_CARD_NUMBER_ID)) {
            statement.setLong(1, currentCreditCard);
            statement.setLong(2, currentCreditCard);
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

    private List<Payment> getPayments(long currentCreditCard, String sortingCriteria, int limit) throws DatabaseException {
        try (PreparedStatement statement =
                     connection.prepareStatement(sortingCriteria)) {
            statement.setLong(1, currentCreditCard);
            statement.setLong(2, currentCreditCard);
            statement.setInt(3, limit);
            statement.execute();
            return getPaymentsFromResultSet(statement.getResultSet(), currentCreditCard);
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    private List<Payment> getPaymentsFromResultSet(ResultSet resultSet, long currentCreditCard) throws DatabaseException {
        List<Payment> payments = new ArrayList<>();
        try {
            while (resultSet.next()) {
                if (resultSet.getLong("credit_card_id_source") == currentCreditCard) {
                    payments.add(new PaymentBuilder()
                            .setId(resultSet.getLong("id"))
                            .setMoney(resultSet.getDouble("money") * -1)
                            .setDescription(resultSet.getString("description"))
                            .setPaymentStatus(PaymentStatus.valueOf(resultSet.getString("status").toUpperCase()))
                            .setDate(LocalDateTime.parse(resultSet.getString("date")))
                            .setCreditCardIdSource(resultSet.getLong("credit_card_id_source"))
                            .setCreditCardIdDestination(resultSet.getLong("credit_card_id_destination"))
                            .setPaymentCategory(PaymentCategory.values()[resultSet.getInt("category_id") - 1])
                            .build());
                } else {
                    payments.add(new PaymentBuilder()
                            .setId(resultSet.getLong("id"))
                            .setMoney(resultSet.getDouble("money"))
                            .setDescription(resultSet.getString("description"))
                            .setPaymentStatus(PaymentStatus.valueOf(resultSet.getString("status").toUpperCase()))
                            .setDate(LocalDateTime.parse(resultSet.getString("date")))
                            .setCreditCardIdSource(resultSet.getLong("credit_card_id_source"))
                            .setCreditCardIdDestination(resultSet.getLong("credit_card_id_destination"))
                            .setPaymentCategory(PaymentCategory.values()[resultSet.getInt("category_id") - 1])
                            .build());
                }
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        return payments;
    }

    @Override
    public List<Payment> getAllCardsByCardNumber() {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getAllCategories() throws DatabaseException {
        List<String> listOfCategories = new ArrayList<>();
        try (Connection connection = basicConnectionPool.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute(QUERY_TO_GET_ALL_CATEGORIES);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                listOfCategories.add(resultSet.getString("name"));
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        return listOfCategories;
    }

}
