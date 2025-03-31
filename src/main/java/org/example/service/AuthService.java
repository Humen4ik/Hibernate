package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.conf.AuthenticationContext;
import org.example.dao.UserDao;
import org.example.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;

    public boolean authenticate(String username, String password) {
        boolean exists = userDao.existByUsernameAndPassword(username, password);
        if (exists) {
            AuthenticationContext.authenticate(username);
            return true;
        }
        return false;
    }

    public void logout() {
        AuthenticationContext.clear();
    }
}