package org.example.dao;

import org.example.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerDao {
    Optional<Trainer> findByUsername(String username);
    Optional<Trainer> saveTrainer(Trainer trainer);
    List<Trainer> findNotAssignedTrainers(String username);
}
