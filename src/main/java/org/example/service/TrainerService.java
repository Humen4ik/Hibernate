package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.TrainerDao;
import org.example.exception.TrainerNotFoundException;
import org.example.model.Trainer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerDao trainerDao;
    private final UserService userService;

    public Trainer findTrainerByUsername(String username) {
        return trainerDao.findByUsername(username)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer with username = " + username + " not found"));
    }

    public void changePassword(String username, String password){
        userService.changePassword(username, password);
    }

}
