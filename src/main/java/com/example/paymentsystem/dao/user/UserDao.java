package com.example.paymentsystem.dao.user;

import com.example.paymentsystem.dao.DAO;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.exception.DatabaseException;

public interface UserDao extends DAO<User> {
    User getUserByEmailAndPassword(String email, String password) throws DatabaseException;

    String getUserSpecifiedNameByCardId(long id) throws DatabaseException;

    void updateUserData(User user) throws DatabaseException;
}
