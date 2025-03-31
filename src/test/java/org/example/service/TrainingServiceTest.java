package org.example.service;

import org.example.dao.TrainingDao;
import org.example.exception.TrainingNotSavedException;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingTypeEntity;
import org.example.model.User;
import org.example.model.enums.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

    @Mock
    private TrainingDao trainingDao;

    @InjectMocks
    private TrainingService trainingService;

    @Test
    void testSaveTraining() {
        User user = new User();
        user.setUsername("Trainer1");
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        // Створюємо тренування
        Training training = new Training();
        training.setTrainingType(new TrainingTypeEntity(1L, TrainingType.YOGA));
        training.setTrainer(trainer);

        // Мокаємо збереження тренування
        when(trainingDao.saveTraining(training)).thenReturn(Optional.of(training));

        // Викликаємо метод
        Training savedTraining = trainingService.saveTraining(training);

        // Перевіряємо результат
        assertNotNull(savedTraining);
        assertEquals("Trainer1", savedTraining.getTrainer().getUser().getUsername());
        assertEquals(TrainingType.YOGA, savedTraining.getTrainingType().getTrainingType());
    }

    @Test
    void testSaveTraining_throwsExceptionWhenNotSaved() {
        User user = new User();
        user.setUsername("Trainer1");
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        // Створюємо тренування
        Training training = new Training();
        training.setTrainingType(new TrainingTypeEntity(1L, TrainingType.YOGA));
        training.setTrainer(trainer);

        // Мокаємо невдале збереження тренування
        when(trainingDao.saveTraining(training)).thenReturn(Optional.empty());

        // Перевіряємо, що кидається виключення
        assertThrows(TrainingNotSavedException.class, () -> trainingService.saveTraining(training));
    }



}