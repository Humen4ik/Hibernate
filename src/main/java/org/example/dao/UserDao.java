package org.example.dao;

import org.example.model.User;

import java.util.List;

public interface UserDao {
    Boolean existByUsernameAndPassword(String username, String password);
    void changePassword(String username, String newPassword);
    void activateUser(String username);
    void deactivateUser(String username);
    List<User> findAll();
}
