package org.example.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.example.model.Trainee;
import org.example.model.Training;
import org.example.model.enums.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TrainingDaoImplTest {

    @Mock private Root<Training> root;

    @Mock private Join<Training, Trainee> traineeJoin;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Training> criteriaQuery;

    @Mock
    private Root<Training> trainingRoot;

    @Mock
    private TypedQuery<Training> typedQuery;

    @InjectMocks
    private TrainingDaoImpl trainingDao;

    @Test
    void testFindAllTrainingsByTraineeUsernameCriteria() {
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        List<Training> result = trainingDao.findAllTrainingsByTraineeUsernameCriteria("trainee1", LocalDate.now(), LocalDate.now().plusDays(7), "trainer1", TrainingType.YOGA);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(entityManager).createQuery(criteriaQuery);
    }

    @Test
    void testFindAllTrainingsByTrainerUsernameCriteria() {
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        List<Training> result = trainingDao.findAllTrainingsByTrainerUsernameCriteria("trainer1", LocalDate.now(), LocalDate.now().plusDays(7), "trainee1");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(entityManager).createQuery(criteriaQuery);
    }

    @Test
    void testSaveTraining_NewTraining() {
        Training training = new Training();
        training.setId(null);

        doNothing().when(entityManager).persist(training);

        Optional<Training> result = trainingDao.saveTraining(training);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());
        verify(entityManager).persist(training);
    }

    @Test
    void testSaveTraining_ExistingTraining() {
        Training training = new Training();
        training.setId(1L);

        when(entityManager.merge(training)).thenReturn(training);

        Optional<Training> result = trainingDao.saveTraining(training);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());
        verify(entityManager).merge(training);
    }
}