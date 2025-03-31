package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.dao.UserDao;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PersonalDataService personalDataService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Ініціалізація моків перед кожним тестом
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnListOfUsers() {
        // Підготовка моків
        User user1 = new User();
        user1.setUsername("john.doe");
        user1.setPassword("password123");
        User user2 = new User();
        user2.setUsername("jane.doe");
        user2.setPassword("password456");
        when(userDao.findAll()).thenReturn(List.of(user1, user2));

        // Виклик методу
        List<User> result = userService.findAll();

        // Перевірки
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("john.doe", result.get(0).getUsername());
        assertEquals("jane.doe", result.get(1).getUsername());
        verify(userDao).findAll(); // Перевірка, що findAll був викликаний
    }

}
