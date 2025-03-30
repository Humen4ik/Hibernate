package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.TrainingTypeDao;
import org.example.exception.TrainingTypeEntityNotFoundException;
import org.example.model.TrainingTypeEntity;
import org.example.model.enums.TrainingType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingTypeEntityService {

    private final TrainingTypeDao trainingTypeDao;

    public TrainingTypeEntity findTrainingTypeByTrainingType(TrainingType trainingType) {
        return trainingTypeDao.findTrainingTypeByName(trainingType)
                .orElseThrow(() -> new TrainingTypeEntityNotFoundException("Training Type Not Found"));
    }

}
