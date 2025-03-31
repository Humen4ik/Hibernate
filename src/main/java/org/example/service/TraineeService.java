package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dao.TraineeDao;
import org.example.exception.TraineeNotFoundException;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraineeService {

    private final TraineeDao traineeDao;
    private final TrainerService trainerService;
    private final UserService userService;

    public Trainee saveTrainee(Trainee trainee){
        String username = PersonalDataService.generateUsername(trainee.getUser(), userService.findAll());
        String password = PersonalDataService.generatePassword();
        trainee.getUser().setPassword(password);
        trainee.getUser().setUsername(username);
        return traineeDao.saveTrainee(trainee).orElseThrow(() -> new TraineeNotFoundException("Trainee not found"));
    }

    public Trainee findTraineeByUsername(String username){
        return traineeDao.findByUsername(username)
                .orElseThrow(() -> new TraineeNotFoundException("Trainee with username = " + username + " not found"));
    }

    public void changePassword(String username){
        userService.changePassword(username);
    }

    @Transactional
    public Trainee updateTraineeByUsername(Trainee newTrainee, String username) {
        Trainee oldTrainee = findTraineeByUsername(username);
        if (newTrainee.getUser().getFirstName() != null && !newTrainee.getUser().getFirstName().isBlank()) {
            oldTrainee.getUser().setFirstName(newTrainee.getUser().getFirstName());
        }
        if (newTrainee.getUser().getLastName() != null && !newTrainee.getUser().getLastName().isBlank()) {
            oldTrainee.getUser().setLastName(newTrainee.getUser().getLastName());
        }
        if (newTrainee.getUser().getIsActive() != null) {
            oldTrainee.getUser().setIsActive(newTrainee.getUser().getIsActive());
        }
        if (newTrainee.getDateOfBirth() != null) {
            oldTrainee.setDateOfBirth(newTrainee.getDateOfBirth());
        }
        if (newTrainee.getAddress() != null && !newTrainee.getAddress().isBlank()) {
            oldTrainee.setAddress(newTrainee.getAddress());
        }
        return findTraineeByUsername(username);
    }

    public void deleteTraineeByUsername(String username) {
        traineeDao.deleteByUsername(username);
    }

    @Transactional
    public void updateTraineesTrainersByUsername(Set<String> trainersUsernames, String username) {
        Trainee trainee = findTraineeByUsername(username);

        Set<Trainer> trainers = trainersUsernames.stream()
                .map(trainerService::findTrainerByUsername)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        trainee.setTrainers(trainers);
    }
}
