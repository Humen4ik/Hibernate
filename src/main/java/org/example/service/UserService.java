package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserDao;
import org.example.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void changePassword(String username) {
        String newPassword = PersonalDataService.generatePassword();
        userDao.changePassword(username, newPassword);
    }

}
