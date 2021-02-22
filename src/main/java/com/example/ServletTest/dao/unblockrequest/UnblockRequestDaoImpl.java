package com.example.ServletTest.dao.unblockrequest;

import com.example.ServletTest.connectionpool.BasicConnectionPool;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.unblockingrequest.UnblockingRequest;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnblockRequestDaoImpl implements UnblockRequestDao {
    private static final Logger logger = Logger.getLogger(UnblockRequestDaoImpl.class);
    private Connection connection;
    private BasicConnectionPool basicConnectionPool;
    private static UnblockRequestDaoImpl instance;
    private static final String QUERY_TO_CREATE_UNBLOCKING_REQUEST =
            "INSERT INTO request_blocked SET " +
                    "description = ?, " +
                    "credit_card_id = ?, status = ?";
    private static final String QUERY_TO_GET_ALL_UNAPPROVED_REQUESTS =
            "SELECT * FROM request_blocked WHERE status = 0";
    private static final String QUERY_TO_UPDATE_UNBLOCKING_REQUEST_STATUS =
            "UPDATE request_blocked SET status = ? " +
                    "WHERE credit_card_id = ?";



    private UnblockRequestDaoImpl() {
        try {
            basicConnectionPool = BasicConnectionPool.create();
            connection = basicConnectionPool.getConnection();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
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
        throw new NotImplementedException();
    }

    @Override
    public List<UnblockingRequest> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public UnblockingRequest save(UnblockingRequest unblockingRequest) throws DatabaseException {
        try (PreparedStatement statement =
                     connection.prepareStatement(QUERY_TO_CREATE_UNBLOCKING_REQUEST,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, unblockingRequest.getDescription());
            statement.setLong(2, unblockingRequest.getCreditCardId());
            statement.setInt(3, unblockingRequest.getRequestStatus().ordinal());
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
                        throw new DatabaseException("Failed to create payment, no obtained id");
                    }
                }
            }
        } catch (SQLException | DatabaseException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        return unblockingRequest;
    }

    @Override
    public void update(UnblockingRequest unblockingRequest, String[] params) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(UnblockingRequest unblockingRequest) {
        throw new NotImplementedException();
    }

    @Override
    public List<UnblockingRequest> getAllUnapprovedRequests() throws DatabaseException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(QUERY_TO_GET_ALL_UNAPPROVED_REQUESTS);
            return getUnblockingRequestsFromResultSet(statement.getResultSet());
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    @Override
    public void changeUnblockingRequestStatus(long cardId, UnblockingRequest.RequestStatus status)
            throws DatabaseException {
        try (PreparedStatement statement =
                     connection.prepareStatement(QUERY_TO_UPDATE_UNBLOCKING_REQUEST_STATUS)) {
            statement.setInt(1, status.ordinal());
            statement.setLong(2, cardId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    private List<UnblockingRequest> getUnblockingRequestsFromResultSet(ResultSet resultSet) throws SQLException {
        List<UnblockingRequest> unblockingRequests = new ArrayList<>();
        while (resultSet.next()) {
             unblockingRequests.add(buildUnblockingRequest(resultSet));
        }
        logger.info("All unblocking request with not approved status are retrieved");
        return unblockingRequests;
    }

    private UnblockingRequest buildUnblockingRequest(ResultSet resultSet) throws SQLException {
        return new UnblockingRequest(resultSet.getLong("id"),
                resultSet.getString("description"),
                resultSet.getLong("credit_card_id"),
                UnblockingRequest.RequestStatus.values()[resultSet.getInt("status")]
        );
    }
}
