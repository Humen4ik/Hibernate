package org.example.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class TrainerDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Trainer> query;

    @InjectMocks
    private TrainerDaoImpl trainerDao;

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(new User(null, "Trainer", "User", "trainerUser", "password", true));

    }

    @Test
    void testFindByUsername_WhenTrainerExists() {
        when(entityManager.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter("username", "trainerUser")).thenReturn(query);
        when(query.getResultStream()).thenReturn(Stream.of(trainer));

        Optional<Trainer> result = trainerDao.findByUsername("trainerUser");

        assertTrue(result.isPresent());
        assertEquals("trainerUser", result.get().getUser().getUsername());
    }

    @Test
    void testFindByUsername_WhenTrainerDoesNotExist() {
        when(entityManager.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter("username", "unknownUser")).thenReturn(query);
        when(query.getResultStream()).thenReturn(Stream.<Trainer>empty());

        Optional<Trainer> result = trainerDao.findByUsername("unknownUser");

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveTrainer_WhenNewTrainer() {
        trainer.setId(null);
        doNothing().when(entityManager).persist(trainer);

        Optional<Trainer> result = trainerDao.saveTrainer(trainer);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
        verify(entityManager).persist(trainer);
    }

    @Test
    void testSaveTrainer_WhenExistingTrainer() {
        when(entityManager.merge(trainer)).thenReturn(trainer);

        Optional<Trainer> result = trainerDao.saveTrainer(trainer);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
        verify(entityManager).merge(trainer);
    }



}
