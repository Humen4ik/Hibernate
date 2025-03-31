package org.example.facade;

import lombok.RequiredArgsConstructor;
import org.example.conf.AuthenticationContext;
import org.example.dto.TraineeDto;
import org.example.mapper.TraineeMapper;
import org.example.model.Trainee;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TraineeFacade {

    private final TraineeService traineeService;
    private final TraineeMapper traineeMapper;
    private final TrainerService trainerService;
    private final UserService userService;

    public TraineeDto saveTrainee(TraineeDto traineeDto) {
        Trainee trainee = traineeMapper.toEntity(traineeDto, trainerService);
        return traineeMapper.toDto(traineeService.saveTrainee(trainee));
    }

    public TraineeDto findTraineeByUsername(String username) {
        AuthenticationContext.requireAuthentication();
        return traineeMapper.toDto(traineeService.findTraineeByUsername(username));
    }

    public void changePassword(String username) {
        AuthenticationContext.requireAuthentication();
        traineeService.changePassword(username);
    }

    public TraineeDto updateTraineeByUsername(TraineeDto traineeDto, String username) {
        AuthenticationContext.requireAuthentication();
        Trainee newTrainee = traineeMapper.toEntity(traineeDto, trainerService);
        Trainee updatedTrainee = traineeService.updateTraineeByUsername(newTrainee, username);
        return traineeMapper.toDto(updatedTrainee);
    }

    public void deleteTraineeByUsername(String username) {
        AuthenticationContext.requireAuthentication();
        traineeService.deleteTraineeByUsername(username);
    }

    public void updateTraineeTrainersByUsername(Set<String> trainers, String username) {
        AuthenticationContext.requireAuthentication();
        traineeService.updateTraineesTrainersByUsername(trainers, username);
    }

    public boolean getTraineeActivityStatus(String username) {
        AuthenticationContext.requireAuthentication();
        return userService.changeUserActivity(username);
    }
}
