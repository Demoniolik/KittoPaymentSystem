package com.example.ServletTest.dao.unblockrequest;

import com.example.ServletTest.connectionpool.BasicConnectionPool;
import com.example.ServletTest.model.unblockingrequest.UnblockingRequest;
import com.mysql.cj.xdevapi.PreparableStatement;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class UnblockRequestDaoImpl implements UnblockRequestDao {
    private static final Logger logger = Logger.getLogger(UnblockRequestDaoImpl.class);
    private Connection connection;
    private BasicConnectionPool basicConnectionPool;
    private static UnblockRequestDaoImpl instance;
    private static final String QUERY_TO_CREATE_UNBLOCKING_REQUEST =
            "INSERT INTO request_blocked SET " +
                    "description = ?, " +
                    "credit_card_id = ?";


    private UnblockRequestDaoImpl() {
        try {
            basicConnectionPool = BasicConnectionPool.create();
            connection = basicConnectionPool.getConnection();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            //TODO: throw new database exception
            logger.error(exception);
        }
    }

    public static UnblockRequestDaoImpl getInstance() {
        if (instance == null) {
            instance = new UnblockRequestDaoImpl();
        }
        return instance;
    }

    @Override
    public UnblockingRequest get(long id) {
        return null;
    }

    @Override
    public List<UnblockingRequest> getAll() {
        return null;
    }

    @Override
    public UnblockingRequest save(UnblockingRequest unblockingRequest) {
        try (PreparedStatement statement =
                     connection.prepareStatement(QUERY_TO_CREATE_UNBLOCKING_REQUEST,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, unblockingRequest.getDescription());
            statement.setLong(2, unblockingRequest.getCreditCardId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Request wasn't created");
            } else {
                logger.info("Request creation was successful");
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        unblockingRequest.setId(generatedKeys.getLong(1));
                    }else {
                        logger.error("Failed to create payment, no obtained id");
                        // TODO: here you need to throw database exception
                    }
                }
            }
        } catch (SQLException exception) {
            //TODO: create database exception that holds of error
            logger.error(exception);
        }
        return unblockingRequest;
    }

    @Override
    public void update(UnblockingRequest unblockingRequest, String[] params) {

    }

    @Override
    public void delete(UnblockingRequest unblockingRequest) {

    }
}
