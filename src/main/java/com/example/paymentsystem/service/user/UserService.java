package com.example.paymentsystem.service.user;

import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.dao.user.UserDao;
import com.example.paymentsystem.exception.DatabaseException;
import org.apache.log4j.Logger;

import java.util.List;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserByCredentials(String email, String password) throws DatabaseException {
        logger.info("Retrieving user from database by email and password");
        return userDao.getUserByEmailAndPassword(email, password);
    }

    public boolean register(User user) throws DatabaseException {
        logger.info("Registering new user");
        return user != null && userDao.save(user).getId() != -1;
    }

    public String getSpecifiedUserNameByCardId(long creditCardId) throws DatabaseException {
        logger.info("Getting user's specified name by credit card that belongs to user");
        return userDao.getUserSpecifiedNameByCardId(creditCardId);
    }

    public void updateUserData(User user) throws DatabaseException {
        logger.info("Changing data about user");
        userDao.updateUserData(user);
    }

    public List<User> getAllUsers() throws DatabaseException {
        logger.info("Retrieving all users from database");
        return userDao.getAll();
    }

    public User getUserById(long userId) throws DatabaseException {
        logger.info("Retrieving user by id");
        return userDao.get(userId);
    }
}
