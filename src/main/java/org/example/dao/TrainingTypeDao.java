package org.example.dao;

import org.example.model.Training;
import org.example.model.TrainingTypeEntity;
import org.example.model.enums.TrainingType;

import java.util.Optional;

public interface TrainingTypeDao {
    Optional<TrainingTypeEntity> findTrainingTypeByName(TrainingType trainingType);
}
