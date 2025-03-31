package org.example.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.example.model.TrainingTypeEntity;
import org.example.model.enums.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainingTypeDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TrainingTypeDaoImpl trainingTypeDao;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findTrainingTypeByName_ShouldReturnTrainingType() {
        // Підготовка моків
        TrainingType trainingType = TrainingType.CARDIO;
        TrainingTypeEntity expectedEntity = new TrainingTypeEntity();
        expectedEntity.setTrainingType(trainingType);

        TypedQuery<TrainingTypeEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(TrainingTypeEntity.class))).thenReturn(query);
        when(query.setParameter(eq("trainingType"), eq(trainingType))).thenReturn(query);
        when(query.getResultStream()).thenReturn(Stream.of(expectedEntity));

        // Виклик методу
        Optional<TrainingTypeEntity> result = trainingTypeDao.findTrainingTypeByName(trainingType);

        // Перевірки
        assertTrue(result.isPresent());
        assertEquals(trainingType, result.get().getTrainingType());
        verify(entityManager).createQuery(anyString(), eq(TrainingTypeEntity.class)); // Перевірка, що createQuery був викликаний
        verify(query).setParameter(eq("trainingType"), eq(trainingType)); // Перевірка, що setParameter був викликаний
    }

    @Test
    void findTrainingTypeByName_ShouldReturnEmptyOptional_WhenNotFound() {
        // Підготовка моків
        TrainingType trainingType = TrainingType.CARDIO;

        TypedQuery<TrainingTypeEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(TrainingTypeEntity.class))).thenReturn(query);
        when(query.setParameter(eq("trainingType"), eq(trainingType))).thenReturn(query);
        when(query.getResultStream()).thenReturn(Stream.empty()); // Повертаємо порожній стрім

        // Виклик методу
        Optional<TrainingTypeEntity> result = trainingTypeDao.findTrainingTypeByName(trainingType);

        // Перевірки
        assertFalse(result.isPresent());
        verify(entityManager).createQuery(anyString(), eq(TrainingTypeEntity.class)); // Перевірка, що createQuery був викликаний
        verify(query).setParameter(eq("trainingType"), eq(trainingType)); // Перевірка, що setParameter був викликаний
    }
}
