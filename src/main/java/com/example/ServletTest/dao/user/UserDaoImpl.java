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
    private static UserDaoImpl instance;
    private static final String QUERY_TO_GET_ALL_USERS = "SELECT * FROM user";
    private static final String QUERY_TO_GET_USER = "SELECT * FROM user WHERE id = ?";
    private static final String QUERY_TO_UPDATE_USER = "UPDATE user WHERE id = ?" +
                                                        "SET id = ?, first_name = ?," +
                                                        "second_name = ?, login = ?," +
                                                        "password = ?, blocked = ?" +
                                                        "user_type = ?";
    private static final String QUERY_TO_DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String QUERY_TO_GET_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE email = ? " +
                                                                                                "AND password = ?";

    private UserDaoImpl() {
        // TODO: load all the constant queries from the properties file

    }

    public static UserDaoImpl getInstance() {
        if (instance != null) {
            return instance;
        }
        return new UserDaoImpl();
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
    public void save() {

    }

    @Override
    public void update(User o, String[] params) {

    }

    @Override
    public void delete(User o) {

    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        try {
            BasicConnectionPool connectionPool = BasicConnectionPool.create();
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY_TO_GET_USER_BY_EMAIL_AND_PASSWORD);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.execute();
            return getUserFromResultSet(statement.getResultSet());
        } catch (SQLException ex) {
            // TODO: Throw custom database exception
            logger.error(ex);
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
                        .setUserType(UserType.values()[resultSet.getInt("userTypeId") - 1])
                        .build();
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            // TODO: throw database exception
        }
        return user;
    }
}
