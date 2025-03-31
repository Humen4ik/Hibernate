package org.example.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.User;
import org.example.model.enums.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainingDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TrainingDaoImpl trainingDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void saveTraining_ShouldPersistNewTraining() {
        Training training = new Training();
        training.setId(null); // Нове тренування без ID

        // Виклик методу
        Optional<Training> result = trainingDao.saveTraining(training);

        // Перевірки
        assertTrue(result.isPresent());
        verify(entityManager).persist(training); // Перевірка, що persist був викликаний
    }

    @Test
    void saveTraining_ShouldUpdateExistingTraining() {
        Training training = new Training();
        training.setId(1L); // Існуюче тренування з ID

        // Підготовка моків
        when(entityManager.merge(training)).thenReturn(training); // Мок для методу merge

        // Виклик методу
        Optional<Training> result = trainingDao.saveTraining(training);

        // Перевірки
        assertTrue(result.isPresent());
        verify(entityManager).merge(training); // Перевірка, що merge був викликаний
    }

    @Test
    void saveTraining_ShouldReturnEmptyIfExceptionOccurs() {
        Training training = new Training();
        training.setId(1L); // Існуюче тренування з ID

        // Підготовка моків
        when(entityManager.merge(training)).thenThrow(new RuntimeException("DB error")); // Мок для помилки

        // Виклик методу
        Optional<Training> result = trainingDao.saveTraining(training);

        // Перевірки
        assertFalse(result.isPresent());
    }
}
