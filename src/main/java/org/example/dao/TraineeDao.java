package org.example.dao;

import org.example.model.Trainee;

import java.util.Optional;

public interface TraineeDao {
    Optional<Trainee> findByUsername(String username);
    Optional<Trainee> saveTrainee(Trainee trainee);
    void deleteByUsername(String username);
}
