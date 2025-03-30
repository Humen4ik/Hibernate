package org.example.facade;

import lombok.RequiredArgsConstructor;
import org.example.dto.TraineeDto;
import org.example.mapper.TraineeMapper;
import org.example.model.Trainee;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraineeFacade {

    private final TraineeService traineeService;
    private final TraineeMapper traineeMapper;
    private final TrainerService trainerService;

    public TraineeDto saveTrainee(TraineeDto traineeDto) {
        Trainee trainee = traineeMapper.toEntity(traineeDto, trainerService);
        return traineeMapper.toDto(traineeService.saveTrainee(trainee));
    }

    public TraineeDto findTraineeByUsername(String username) {
        return traineeMapper.toDto(traineeService.findTraineeByUsername(username));
    }

    public void changePassword(String username) {
        traineeService.changePassword(username);
    }

    public TraineeDto updateTraineeByUsername(TraineeDto traineeDto, String username) {
        Trainee newTrainee = traineeMapper.toEntity(traineeDto, trainerService);
        Trainee updatedTrainee = traineeService.updateTraineeByUsername(newTrainee, username);
        return traineeMapper.toDto(updatedTrainee);
    }

    public void deleteTraineeByUsername(String username) {
        traineeService.deleteTraineeByUsername(username);
    }
}
