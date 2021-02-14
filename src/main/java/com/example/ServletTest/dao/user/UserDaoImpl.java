package com.example.ServletTest.dao.user;

import com.example.ServletTest.connectionpool.BasicConnectionPool;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.model.user.UserBuilder;
import com.example.ServletTest.model.user.UserType;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private static final String QUERY_TO_GET_ALL_USERS = "SELECT * FROM user";
    private static final String QUERY_TO_GET_USER = "SELECT * FROM user WHERE id = ?";
    private static final String QUERY_TO_UPDATE_USER = "UPDATE user WHERE id = ?" +
                                                        "SET id = ?, first_name = ?," +
                                                        "second_name = ?, login = ?," +
                                                        "password = ?, blocked = ?" +
                                                        "user_type_id = ?";
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
            // TODO: throw database exception
        }
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public User get(long id) {
        return null;
    }



    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User save(User user) {
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
                        // TODO: here you need to throw database exception
                    }
                }
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            // TODO: throw database exception
        }
        return user;
    }

    @Override
    public void update(User o, String[] params) {

    }

    @Override
    public void delete(User o) {

    }

    @Override
    public User getUserByEmailAndPassword(String login, String password) {
        logger.info("Searching for user by his/her login and password");
        try (PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_USER_BY_EMAIL_AND_PASSWORD);) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();
            return getUserFromResultSet(statement.getResultSet());
        } catch (SQLException ex) {
            // TODO: Throw custom database exception
            logger.error(ex);
        }
        return null;
    }

    @Override
    public String getUserSpecifiedNameByCardId(long creditCardId) {
        try (PreparedStatement statement =
                    connection.prepareStatement(QUERY_TO_GET_USER_SPECIFIED_NAME_BY_CARD_ID)) {
            statement.setLong(1, creditCardId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException exception) {
            //TODO: database exception
            logger.error(exception);
        }
        return null;
    }

    private User getUserFromResultSet(ResultSet resultSet) {
        User user = null;
        try {
            if (resultSet.next()) {
                user = new UserBuilder()
                        .setId(resultSet.getLong("id"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setLastName(resultSet.getString("second_name"))
                        .setLogin(resultSet.getString("login"))
                        .setPassword(resultSet.getString("password"))
                        .setBlocked(resultSet.getBoolean("blocked"))
                        .setUserType(UserType.values()[resultSet.getInt("user_type_id") - 1])
                        .build();
                logger.info("User was found and packed in object");
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            // TODO: throw database exception
        }
        return user;
    }
}
