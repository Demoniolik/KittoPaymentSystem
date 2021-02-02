package com.example.ServletTest.service;

import com.example.ServletTest.dao.user.UserDaoImpl;
import com.example.ServletTest.model.user.User;

public class UserService {
    public User getUserByCredentials(String email, String password) {
        // TODO: Here we need to get user in database and build it in case it exists
        return UserDaoImpl.getInstance().getUserByEmailAndPassword(email, password);
    }

    public boolean register(User user) {
        return false;
    }
}
