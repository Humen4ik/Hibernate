package org.example.dao;

import org.example.model.Trainee;

import java.util.Optional;

public interface TraineeDao {

    void save(Trainee t);
    void update(Trainee t);
    void deleteByUsername(String username);
    Optional<Trainee> getTraineeByUsername(String username);
    void activateTrainee(String username);
    void deactivateTrainee(String username);
    void changePassword(String username, String password);

}
