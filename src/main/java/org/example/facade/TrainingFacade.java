package org.example.facade;

import lombok.RequiredArgsConstructor;
import org.example.conf.AuthenticationContext;
import org.example.dto.TrainingDto;
import org.example.mapper.TrainingMapper;
import org.example.model.Training;
import org.example.model.enums.TrainingType;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.TrainingTypeEntityService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingFacade {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingTypeEntityService trainingTypeEntityService;

    public TrainingDto saveTraining(TrainingDto trainingDto) {
        AuthenticationContext.requireAuthentication();
        Training training = trainingMapper.toEntity(trainingDto, traineeService, trainerService, trainingTypeEntityService);
        return trainingMapper.toDto(trainingService.saveTraining(training));
    }

    public List<TrainingDto> findAllTrainingsByTraineeUsernameCriteria(String username, LocalDate fromDate, LocalDate toDate,
                                                        String trainerName, TrainingType trainingType) {
        AuthenticationContext.requireAuthentication();
        return trainingMapper.toDtoList(trainingService.findAllTrainingsByTraineeUsernameCriteria(username, fromDate, toDate, trainerName, trainingType));
    }

    public List<TrainingDto> findAllTrainingsByTrainerUsernameCriteria(String username, LocalDate fromDate, LocalDate toDate,
                                                                       String trainerName) {
        AuthenticationContext.requireAuthentication();
        return trainingMapper.toDtoList(trainingService.findAllTrainingsByTrainerUsernameCriteria(username, fromDate, toDate, trainerName));
    }

}
