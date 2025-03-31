package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dao.TrainerDao;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.TrainingTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerDao trainerDao;
    private final UserService userService;
    private final TrainingTypeEntityService trainingTypeEntityService;

    @Transactional
    public Trainer updateTrainerByUsername(Trainer newTrainer, String username) {
        Trainer oldTrainer = findTrainerByUsername(username);

        if (newTrainer.getUser().getFirstName() != null && !newTrainer.getUser().getFirstName().isBlank()) {
            oldTrainer.getUser().setFirstName(newTrainer.getUser().getFirstName());
        }
        if (newTrainer.getUser().getLastName() != null && !newTrainer.getUser().getLastName().isBlank()) {
            oldTrainer.getUser().setLastName(newTrainer.getUser().getLastName());
        }
        if (newTrainer.getUser().getIsActive() != null) {
            oldTrainer.getUser().setIsActive(newTrainer.getUser().getIsActive());
        }
        if (newTrainer.getSpecialization() != null && newTrainer.getSpecialization().getTrainingType() != null) {
            TrainingTypeEntity trainingTypeEntity = trainingTypeEntityService.findTrainingTypeByTrainingType(newTrainer.getSpecialization().getTrainingType());
            oldTrainer.setSpecialization(trainingTypeEntity);
        }

        return oldTrainer;
    }


    public Trainer findTrainerByUsername(String username) {
        return trainerDao.findByUsername(username)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer with username = " + username + " not found"));
    }

    public void changePassword(String username){
        userService.changePassword(username);
    }

    @Transactional
    public Trainer saveTrainer(Trainer trainer) {
        TrainingTypeEntity trainingTypeEntity = trainingTypeEntityService.findTrainingTypeByTrainingType(trainer.getSpecialization().getTrainingType());
        String username = PersonalDataService.generateUsername(trainer.getUser(), userService.findAll());
        String password = PersonalDataService.generatePassword();
        trainer.getUser().setPassword(password);
        trainer.getUser().setUsername(username);
        trainer.setSpecialization(trainingTypeEntity);
        return trainerDao.saveTrainer(trainer).orElseThrow(() -> new TraineeNotFoundException("Trainer not found"));
    }

    public List<Trainer> findAllUnassignedTrainers(String traineeUsername) {
        return trainerDao.findNotAssignedTrainers(traineeUsername);
    }

}
