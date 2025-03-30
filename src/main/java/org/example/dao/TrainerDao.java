package org.example.dao;

import org.example.model.Trainer;

import java.util.Optional;

public interface TrainerDao {
    public Optional<Trainer> findByUsername(String username);
    Optional<Trainer> saveTrainer(Trainer trainer);
}
