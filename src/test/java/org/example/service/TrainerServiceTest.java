package org.example.service;

import org.example.dao.TrainerDao;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.model.Trainer;
import org.example.model.TrainingTypeEntity;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @Mock
    private TrainerDao trainerDao;

    @Mock
    private UserService userService;

    @Mock
    private TrainingTypeEntityService trainingTypeEntityService;

    @Mock
    private TrainingTypeEntity trainingTypeEntity;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    void testUpdateTrainerByUsername() {
        String username = "trainer1";
        Trainer newTrainer = new Trainer();
        newTrainer.setUser(new User());
        newTrainer.getUser().setFirstName("NewFirstName");
        newTrainer.getUser().setLastName("NewLastName");

        Trainer oldTrainer = new Trainer();
        oldTrainer.setUser(new User());
        oldTrainer.getUser().setFirstName("OldFirstName");
        oldTrainer.getUser().setLastName("OldLastName");

        when(trainerDao.findByUsername(username)).thenReturn(Optional.of(oldTrainer));

        Trainer updatedTrainer = trainerService.updateTrainerByUsername(newTrainer, username);

        assertNotNull(updatedTrainer);
        assertEquals("NewFirstName", updatedTrainer.getUser().getFirstName());
        assertEquals("NewLastName", updatedTrainer.getUser().getLastName());
    }

    @Test
    void testFindTrainerByUsername_ThrowsExceptionWhenNotFound() {
        String username = "trainer1";

        when(trainerDao.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.findTrainerByUsername(username));
    }

    @Test
    void testFindTrainerByUsername_ReturnsTrainer() {
        String username = "trainer1";
        Trainer trainer = new Trainer();
        trainer.setUser(new User());

        when(trainerDao.findByUsername(username)).thenReturn(Optional.of(trainer));

        Trainer foundTrainer = trainerService.findTrainerByUsername(username);

        assertNotNull(foundTrainer);
        assertEquals(trainer, foundTrainer);
    }

    @Test
    void testChangePassword() {
        String username = "trainer1";

        // Assuming changePassword method is mocked correctly.
        doNothing().when(userService).changePassword(username);

        trainerService.changePassword(username);

        verify(userService, times(1)).changePassword(username);
    }

    @Test
    void testSaveTrainer() {
        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        trainer.getUser().setFirstName("FirstName");
        trainer.getUser().setLastName("LastName");
        trainer.setSpecialization(new TrainingTypeEntity());

        when(trainingTypeEntityService.findTrainingTypeByTrainingType(any())).thenReturn(trainingTypeEntity);
        when(userService.findAll()).thenReturn(Collections.emptyList());
        when(trainerDao.saveTrainer(any(Trainer.class))).thenReturn(Optional.of(trainer));

        Trainer savedTrainer = trainerService.saveTrainer(trainer);

        assertNotNull(savedTrainer);
        assertEquals("FirstName", savedTrainer.getUser().getFirstName());
        assertEquals("LastName", savedTrainer.getUser().getLastName());
    }

    @Test
    void testSaveTrainer_ThrowsException() {
        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        trainer.getUser().setFirstName("FirstName");
        trainer.getUser().setLastName("LastName");
        trainer.setSpecialization(new TrainingTypeEntity());

        when(trainingTypeEntityService.findTrainingTypeByTrainingType(any())).thenReturn(trainingTypeEntity);
        when(userService.findAll()).thenReturn(Collections.emptyList());
        when(trainerDao.saveTrainer(any(Trainer.class))).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> trainerService.saveTrainer(trainer));
    }

    @Test
    void testFindAllUnassignedTrainers() {
        String traineeUsername = "trainee1";
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer());

        when(trainerDao.findNotAssignedTrainers(traineeUsername)).thenReturn(trainers);

        List<Trainer> result = trainerService.findAllUnassignedTrainers(traineeUsername);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
