package com.example.ServletTest.service.user;

import com.example.ServletTest.dao.user.UserDao;
import com.example.ServletTest.model.user.User;
import org.apache.log4j.Logger;

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
}
