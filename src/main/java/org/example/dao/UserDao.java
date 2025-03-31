package org.example.dao;

import org.example.model.User;

import java.util.List;

public interface UserDao {
    Boolean existByUsernameAndPassword(String username, String password);
    void changePassword(String username, String newPassword);
    Boolean changeUserActivity(String username);
    List<User> findAll();
}
