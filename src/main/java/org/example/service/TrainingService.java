package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.TrainingDao;
import org.example.exception.TrainingNotSavedException;
import org.example.model.Training;
import org.example.model.enums.TrainingType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingDao trainingDao;

    public Training saveTraining(Training training) {
        Optional<Training> savedTraining = trainingDao.saveTraining(training);
        return savedTraining.orElseThrow(() -> new TrainingNotSavedException("Training not saved"));
    }

    public List<Training> findAllTrainingsByTraineeUsernameCriteria(String username, LocalDate fromDate, LocalDate toDate,
                                          String trainerName, TrainingType trainingType) {
        return trainingDao.findAllTrainingsByTraineeUsernameCriteria(username, fromDate, toDate, trainerName, trainingType);
    }

    public List<Training> findAllTrainingsByTrainerUsernameCriteria(String username, LocalDate fromDate, LocalDate toDate,
                                                                    String trainerName) {
        return trainingDao.findAllTrainingsByTrainerUsernameCriteria(username, fromDate, toDate, trainerName);
    }


}
