package org.example.facade;

import lombok.RequiredArgsConstructor;
import org.example.dto.TraineeDto;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.TrainerService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerFacade {

    private final TrainerService trainerService;

    public TraineeDto saveTrainee(TraineeDto traineeDto) {
//        Trainer trainee = trainerMapper.toEntity(traineeDto, trainerService);
//        return trainerMapper.toDto(trainerService.saveTrainee(trainee));
        return null;
    }

}
