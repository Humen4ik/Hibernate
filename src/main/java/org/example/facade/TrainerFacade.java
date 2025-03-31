package org.example.facade;

import lombok.RequiredArgsConstructor;
import org.example.conf.AuthenticationContext;
import org.example.dto.TraineeDto;
import org.example.dto.TrainerDto;
import org.example.mapper.TrainerMapper;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.TrainerService;
import org.example.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainerFacade {

    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;
    private final UserService userService;

    public TrainerDto saveTrainer(TrainerDto trainerDto) {
        Trainer trainer = trainerMapper.toEntity(trainerDto);
        return trainerMapper.toDto(trainerService.saveTrainer(trainer));
    }

    public void changePassword(String username) {
        AuthenticationContext.requireAuthentication();
        trainerService.changePassword(username);
    }
    
    public TrainerDto updateTrainer(TrainerDto trainerDto, String username) {
        AuthenticationContext.requireAuthentication();
        Trainer newTrainer = trainerMapper.toEntity(trainerDto);
        Trainer updatedTrainer = trainerService.updateTrainerByUsername(newTrainer, username);
        return trainerMapper.toDto(updatedTrainer);
    }

    public TrainerDto findTrainerByUsername(String username) {
        AuthenticationContext.requireAuthentication();
        return trainerMapper.toDto(trainerService.findTrainerByUsername(username));
    }

    public List<TrainerDto> findAllUnAssignedTrainers(String traineeUsername) {
        AuthenticationContext.requireAuthentication();
        List<Trainer> trainers = trainerService.findAllUnassignedTrainers(traineeUsername);
        return trainerMapper.toDtoList(trainers);
    }

    public boolean getTrainerActivityStatus(String trainerUsername) {
        AuthenticationContext.requireAuthentication();
        return userService.changeUserActivity(trainerUsername);
    }
}
