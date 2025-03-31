package org.example.dao;

import org.example.model.Trainee;
import org.example.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TraineeDao {
    Optional<Trainee> findByUsername(String username);
    Optional<Trainee> saveTrainee(Trainee trainee);
    void deleteByUsername(String username);
}
