package org.example.dao;

import org.example.model.Training;
import org.example.model.enums.TrainingType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingDao {
    List<Training> findAllTrainingsByTraineeUsernameCriteria(String traineeUsername, LocalDate fromDate, LocalDate toDate,
                                                     String trainerName, TrainingType trainingType);
    List<Training> findAllTrainingsByTrainerUsernameCriteria(String trainerUsername, LocalDate fromDate, LocalDate toDate,
                                                             String traineeName);
    Optional<Training> saveTraining(Training training);
}
