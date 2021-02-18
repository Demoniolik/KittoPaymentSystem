package com.example.ServletTest.service.user;

import com.example.ServletTest.dao.user.UserDao;
import com.example.ServletTest.model.user.User;
import org.apache.log4j.Logger;

import java.util.List;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserByCredentials(String email, String password) {
        // TODO: Here we need to get user in database and build it in case it exists
        return userDao.getUserByEmailAndPassword(email, password);
    }

    public boolean register(User user) {
        logger.info("Registering new user");
        return user != null && userDao.save(user).getId() != -1;
    }

    public String getSpecifiedUserNameByCardId(long creditCardId) {
        logger.info("Getting user's specified name by credit card that belongs to user");
        return userDao.getUserSpecifiedNameByCardId(creditCardId);
    }

    public void updateUserData(User user) {
        logger.info("Changing data about user");
        userDao.updateUserData(user);
    }

    public List<User> getAllUsers() {
        logger.info("Retrieving all users from database");
        return userDao.getAll();
    }

    public User getUserById(long userId) {
        logger.info("Retrieving user by id");
        return userDao.get(userId);
    }
}
