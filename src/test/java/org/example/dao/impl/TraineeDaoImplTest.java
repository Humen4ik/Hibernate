package org.example.dao.impl;

import org.example.model.Trainee;
import org.example.model.User;
import org.example.dao.impl.TraineeDaoImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Trainee> query;

    @InjectMocks
    private TraineeDaoImpl traineeDao;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "John", "Doe", "johndoe", "pass", true);
        trainee = new Trainee(1L, null, "Test Address", user, null, null);
    }

    @Test
    void testFindByUsername_Found() {
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter("username", "johndoe")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainee);

        Optional<Trainee> result = traineeDao.findByUsername("johndoe");
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void testFindByUsername_NotFound() {
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter("username", "unknown")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        Optional<Trainee> result = traineeDao.findByUsername("unknown");
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveTrainee_NewTrainee() {
        trainee.setId(null);
        doNothing().when(entityManager).persist(trainee);

        Optional<Trainee> result = traineeDao.saveTrainee(trainee);
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void testSaveTrainee_UpdateTrainee() {
        when(entityManager.merge(trainee)).thenReturn(trainee);

        Optional<Trainee> result = traineeDao.saveTrainee(trainee);
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void testDeleteByUsername_Found() {
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter("username", "johndoe")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainee);
        doNothing().when(entityManager).remove(trainee);

        assertDoesNotThrow(() -> traineeDao.deleteByUsername("johndoe"));
        verify(entityManager, times(1)).remove(trainee);
    }

    @Test
    void testDeleteByUsername_NotFound() {
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter("username", "unknown")).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        assertDoesNotThrow(() -> traineeDao.deleteByUsername("unknown"));
    }
}