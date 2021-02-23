package com.example.ServletTest.dao.user;

import com.example.ServletTest.connectionpool.BasicConnectionPool;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.model.user.UserBuilder;
import com.example.ServletTest.model.user.UserType;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private Connection connection;
    private static UserDaoImpl instance;
    private static BasicConnectionPool basicConnectionPool;
    private static final String QUERY_TO_CREATE_USER = "INSERT INTO user SET first_name = ?," +
            "second_name = ?," +
            "login = ?," +
            "password = ?," +
            "blocked = ?," +
            "user_type_id = ?";
    private static final String QUERY_TO_GET_ALL_USERS = "SELECT * FROM user WHERE user_type_id != 2";
    private static final String QUERY_TO_GET_USER = "SELECT * FROM user WHERE id = ?";
    private static final String QUERY_TO_UPDATE_USER = "UPDATE user " +
                                                        "SET first_name = ?, " +
                                                        "second_name = ?, login = ?, " +
                                                        "password = ?, blocked = ? WHERE id = ?";
    private static final String QUERY_TO_DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String QUERY_TO_GET_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE login = ? " +
                                                                                                "AND password = ?";
    private static final String QUERY_TO_GET_USER_SPECIFIED_NAME_BY_CARD_ID =
            "SELECT CONCAT(second_name, ' ', SUBSTRING(first_name, 1, 1), '.') AS specified_name " +
                    "FROM user " +
                    "WHERE id IN (SELECT user_id FROM credit_card WHERE credit_card.id = ?)";

    private UserDaoImpl() {
        // TODO: load all the constant queries from the properties file
        try {
            basicConnectionPool = BasicConnectionPool.create();
            connection = basicConnectionPool.getConnection();
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public User get(long id) throws DatabaseException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_USER)) {
            statement.setLong(1, id);
            statement.execute();
            return getUserFromResultSet(statement.getResultSet());
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }



    @Override
    public List<User> getAll() throws DatabaseException {
        try (Statement statement =
                     connection.createStatement()) {
            statement.execute(QUERY_TO_GET_ALL_USERS);
            return getUsersFromResultSet(statement.getResultSet());
        } catch (SQLException exception) {
            logger.info(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    @Override
    public User save(User user) throws DatabaseException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_CREATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, user.isBlocked());
            statement.setLong(6, user.getUserType().ordinal() + 1);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("User creation is failed");
            }else {
                logger.info("User creation is successful");
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }else {
                        logger.error("Failed to create user, no obtained id");
                        throw new DatabaseException("Failed to create user, no obtained id");
                    }
                }
            }
        } catch (SQLException | DatabaseException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(exception.getMessage());
        }
        return user;
    }

    @Override
    public void update(User o, String[] params) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(User o) {
        throw new NotImplementedException();
    }

    @Override
    public User getUserByEmailAndPassword(String login, String password) throws DatabaseException {
        logger.info("Searching for user by his/her login and password");
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_USER_BY_EMAIL_AND_PASSWORD);) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();
            return getUserFromResultSet(statement.getResultSet());
        } catch (SQLException ex) {
            logger.error(ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public String getUserSpecifiedNameByCardId(long creditCardId) throws DatabaseException {
        try (PreparedStatement statement =
                    connection.prepareStatement(QUERY_TO_GET_USER_SPECIFIED_NAME_BY_CARD_ID)) {
            statement.setLong(1, creditCardId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        return null;
    }

    @Override
    public void updateUserData(User user) throws DatabaseException {
        try (PreparedStatement statement =
                     connection.prepareStatement(QUERY_TO_UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, user.isBlocked());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
            logger.info("User data has been successfully changed");
        } catch (SQLException exception) {
            logger.info(exception);
            throw new DatabaseException(exception.getMessage());
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws DatabaseException {
        try {
            if (resultSet.next()) {
                return buildUserFromResultSet(resultSet);
            }
        } catch (SQLException exception) {
            logger.error(exception);
            throw new DatabaseException(exception.getMessage());
        }
        return null;
    }

    private User buildUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("second_name"))
                .setLogin(resultSet.getString("login"))
                .setPassword(resultSet.getString("password"))
                .setBlocked(resultSet.getBoolean("blocked"))
                .setUserType(UserType.values()[resultSet.getInt("user_type_id") - 1])
                .build();
        logger.info("User was found and packed in object");
        return user;
    }

    private List<User> getUsersFromResultSet(ResultSet resultSet) throws DatabaseException {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                users.add(buildUserFromResultSet(resultSet));
                logger.info("User was found and packed in object");
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            throw new DatabaseException(exception.getMessage());
        }
        return users;
    }
}
