package org.example.facade;

import lombok.RequiredArgsConstructor;
import org.example.dto.TraineeDto;
import org.example.dto.TrainerDto;
import org.example.mapper.TrainerMapper;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.TrainerService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerFacade {

    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;

    public TrainerDto saveTrainer(TrainerDto trainerDto) {
        Trainer trainer = trainerMapper.toEntity(trainerDto);
        return trainerMapper.toDto(trainerService.saveTrainer(trainer));
    }

    public void changePassword(String username) {
        trainerService.changePassword(username);
    }
    
    public TrainerDto updateTrainer(TrainerDto trainerDto, String username) {
        Trainer newTrainer = trainerMapper.toEntity(trainerDto);
        Trainer updatedTrainer = trainerService.updateTrainerByUsername(newTrainer, username);
        return trainerMapper.toDto(updatedTrainer);
    }

}
