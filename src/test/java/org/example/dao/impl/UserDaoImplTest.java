package org.example.dao.impl;

import jakarta.persistence.Query;
import org.example.dao.impl.UserDaoImpl;
import org.example.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Boolean> booleanQuery;

    @Mock
    private TypedQuery<User> userQuery;

    @InjectMocks
    private UserDaoImpl userDao;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John", "Doe", "johndoe", "password", true);
    }

    @Test
    void existByUsernameAndPassword_ShouldReturnTrue_WhenUserExists() {
        String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username AND u.password = :password";
        when(entityManager.createQuery(jpql, Boolean.class)).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("username", "johndoe")).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("password", "password")).thenReturn(booleanQuery);
        when(booleanQuery.getSingleResult()).thenReturn(true);

        boolean result = userDao.existByUsernameAndPassword("johndoe", "password");

        assertTrue(result);
    }

    @Test
    void existByUsernameAndPassword_ShouldReturnFalse_WhenUserDoesNotExist() {
        String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username AND u.password = :password";
        when(entityManager.createQuery(jpql, Boolean.class)).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("username", "unknown")).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("password", "wrongpass")).thenReturn(booleanQuery);
        when(booleanQuery.getSingleResult()).thenReturn(false);

        boolean result = userDao.existByUsernameAndPassword("unknown", "wrongpass");

        assertFalse(result);
    }

    @Test
    void changePassword_ShouldExecuteUpdate() {
        String username = "testUser";
        String newPassword = "newPassword123";

        Query mockQuery = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);
        when(mockQuery.executeUpdate()).thenReturn(1);

        userDao.changePassword(username, newPassword);

        verify(entityManager).createQuery(anyString());
        verify(mockQuery).setParameter("username", username);
        verify(mockQuery).setParameter("password", newPassword);
        verify(mockQuery).executeUpdate();
    }

    @Test
    void activateUser_ShouldExecuteUpdate() {
        String username = "testUser";

        Query mockQuery = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);
        when(mockQuery.executeUpdate()).thenReturn(1);

        userDao.activateUser(username);

        verify(entityManager).createQuery(anyString());
        verify(mockQuery).setParameter("username", username);
        verify(mockQuery).executeUpdate();
    }

    @Test
    void deactivateUser_ShouldExecuteUpdate() {
        String username = "testUser";

        Query mockQuery = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), any())).thenReturn(mockQuery);
        when(mockQuery.executeUpdate()).thenReturn(1);

        userDao.deactivateUser(username);

        verify(entityManager).createQuery(anyString());
        verify(mockQuery).setParameter("username", username);
        verify(mockQuery).executeUpdate();
    }


    @Test
    void findAll_ShouldReturnListOfUsers() {
        String jpql = "SELECT u FROM User u";
        when(entityManager.createQuery(jpql, User.class)).thenReturn(userQuery);
        when(userQuery.getResultList()).thenReturn(List.of(user));

        List<User> result = userDao.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("johndoe", result.get(0).getUsername());
    }
}
