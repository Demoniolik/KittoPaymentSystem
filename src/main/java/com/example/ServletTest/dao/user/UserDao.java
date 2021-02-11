package com.example.ServletTest.dao.user;

import com.example.ServletTest.dao.DAO;
import com.example.ServletTest.model.user.User;

public interface UserDao extends DAO<User> {
    User getUserByEmailAndPassword(String email, String password);

    String getUserFullNameByCardId(long id);
}
