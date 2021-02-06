package com.example.ServletTest.dao.payment;

import com.example.ServletTest.connectionpool.BasicConnectionPool;
import com.example.ServletTest.model.payment.Payment;
import com.example.ServletTest.model.payment.PaymentStatus;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    private static final Logger logger = Logger.getLogger(PaymentDaoImpl.class);
    private BasicConnectionPool basicConnectionPool;
    private static PaymentDaoImpl instance;
    private static final String QUERY_TO_CREATE_PAYMENT = "INSERT INTO payment SET money = ?, " +
            "status = ?, date = ?, credit_card_id_source = ?, " +
            "credit_card_id_destination = ?";
    private static final String QUERY_TO_CHANGE_STATUS = "UPDATE payment SET status = ? WHERE id = ?";

    private PaymentDaoImpl() {
        try {
            basicConnectionPool = BasicConnectionPool.create();
        } catch (SQLException exception) {
            //TODO: throw new database exception
            logger.error(exception);
        }
    }

    public static PaymentDaoImpl getInstance() {
        if (instance != null){
            return instance;
        }
        instance = new PaymentDaoImpl();
        return instance;
    }

    @Override
    public Payment get(long id) {
        return null;
    }

    @Override
    public List<Payment> getAll() {
        return null;
    }

    @Override
    public Payment save(Payment payment) {
        try (Connection connection = basicConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_TO_CREATE_PAYMENT,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, payment.getMoney());
            statement.setLong(2, payment.getPaymentStatus().ordinal() + 1);
            statement.setString(3, payment.getDate().toString());
            statement.setLong(4, payment.getCreditCardIdSource());
            statement.setLong(5, payment.getCreditCardIdDestination());
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
                        // TODO: here you need to throw database exception
                    }
                }
            }
        } catch (SQLException exception) {
            // TODO: throw new database exception
            logger.error(exception);
        }
        return payment;
    }

    @Override
    public void update(Payment payment, String[] params) {

    }

    @Override
    public void delete(Payment payment) {

    }

    @Override
    public void changeStatus(Payment payment) {
        try (Connection connection = basicConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY_TO_CHANGE_STATUS)) {
            statement.setLong(1, payment.getPaymentStatus().ordinal() + 1);
            statement.setLong(2, payment.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            //TODO: throw new database exception
            logger.error(exception);
        }
    }
}
