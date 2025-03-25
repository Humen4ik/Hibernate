package org.example.service;

import org.example.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public final class PersonalDataService {

    public static String generatePassword() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom rand = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int symbolIndex = rand.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(symbolIndex));
        }
        return password.toString();
    }

    public static String generateUsername(User user, List<? extends User> userList) {
        String username = user.getFirstName().concat(".").concat(user.getLastName());
        long countUsersWithSameNames = userList.stream()
                .filter(curUser -> user.getFirstName().equals(curUser.getFirstName()) && user.getLastName().equals(curUser.getLastName()))
                .count();
        return countUsersWithSameNames == 0 ? username : username.concat(Long.toString(countUsersWithSameNames));
    }
}
