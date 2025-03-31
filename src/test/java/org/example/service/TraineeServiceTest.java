package org.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.dao.TraineeDao;
import org.example.exception.TraineeNotFoundException;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.service.PersonalDataService;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

    @Mock
    private TraineeDao traineeDao;
    @Mock
    private TrainerService trainerService;
    @Mock
    private UserService userService;
    @InjectMocks
    private TraineeService traineeService;

    private Trainee trainee;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("John.Doe");

        trainee = new Trainee();
        trainee.setUser(user);
    }

    @Test
    void saveTrainee_ShouldSaveAndReturnTrainee() {
        when(userService.findAll()).thenReturn(List.of());
        when(traineeDao.saveTrainee(any())).thenReturn(Optional.of(trainee));

        Trainee savedTrainee = traineeService.saveTrainee(trainee);

        assertNotNull(savedTrainee);
        verify(traineeDao).saveTrainee(any());
    }

    @Test
    void saveTrainee_ShouldThrowException_WhenSaveFails() {
        when(traineeDao.saveTrainee(any())).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> traineeService.saveTrainee(trainee));
    }

    @Test
    void findTraineeByUsername_ShouldReturnTrainee() {
        when(traineeDao.findByUsername("John.Doe")).thenReturn(Optional.of(trainee));

        Trainee foundTrainee = traineeService.findTraineeByUsername("John.Doe");

        assertNotNull(foundTrainee);
        assertEquals("John.Doe", foundTrainee.getUser().getUsername());
    }

    @Test
    void findTraineeByUsername_ShouldThrowException_WhenNotFound() {
        when(traineeDao.findByUsername("testUser")).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> traineeService.findTraineeByUsername("testUser"));
    }

    @Test
    void changePassword_ShouldCallUserService() {
        doNothing().when(userService).changePassword("testUser");

        traineeService.changePassword("testUser");

        verify(userService).changePassword("testUser");
    }

    @Test
    void updateTraineeByUsername_ShouldUpdateFields() {
        Trainee newTrainee = new Trainee();
        User newUser = new User();
        newUser.setFirstName("NewName");
        newUser.setLastName("NewLastName");
        newUser.setIsActive(true);
        newTrainee.setUser(newUser);
        newTrainee.setAddress("New Address");

        when(traineeDao.findByUsername("testUser")).thenReturn(Optional.of(trainee));

        Trainee updatedTrainee = traineeService.updateTraineeByUsername(newTrainee, "testUser");

        assertEquals("NewName", updatedTrainee.getUser().getFirstName());
        assertEquals("NewLastName", updatedTrainee.getUser().getLastName());
        assertEquals("New Address", updatedTrainee.getAddress());
    }

    @Test
    void deleteTraineeByUsername_ShouldCallDao() {
        doNothing().when(traineeDao).deleteByUsername("testUser");

        traineeService.deleteTraineeByUsername("testUser");

        verify(traineeDao).deleteByUsername("testUser");
    }

    @Test
    void updateTraineesTrainersByUsername_ShouldUpdateTrainers() {
        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        trainer.getUser().setUsername("trainer1");

        when(traineeDao.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        when(trainerService.findTrainerByUsername("trainer1")).thenReturn(trainer);

        traineeService.updateTraineesTrainersByUsername(Set.of("trainer1"), "testUser");

        assertEquals(1, trainee.getTrainers().size());
    }
}