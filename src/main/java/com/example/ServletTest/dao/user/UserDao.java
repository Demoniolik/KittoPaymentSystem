package com.example.ServletTest.dao.user;

import com.example.ServletTest.dao.DAO;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.user.User;

public interface UserDao extends DAO<User> {
    User getUserByEmailAndPassword(String email, String password) throws DatabaseException;

    String getUserSpecifiedNameByCardId(long id) throws DatabaseException;

    void updateUserData(User user) throws DatabaseException;
}
