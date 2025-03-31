package org.example.mapper;

import org.example.dto.TraineeDto;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.TrainingTypeEntity;
import org.example.model.User;
import org.example.model.enums.TrainingType;
import org.example.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeMapperTest {

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TraineeMapperImpl traineeMapper;

    private Trainee trainee;
    private TraineeDto traineeDto;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User traineeUser = new User(1L, "John", "Doe", "John.Doe", "password", true);
        trainee = new Trainee(1L, null, "Mars Street", traineeUser, null, null); // Заповніть решту полів відповідно до вашого класу

        User trainerUser = new User(2L, "Jane", "Doe", "Jane.Doe", "password", true);
        trainer = new Trainer(1L, new TrainingTypeEntity(1L, TrainingType.BOXING), trainerUser); // Заповніть решту полів відповідно до вашого класу

        trainee.setTrainers(Set.of(trainer));

        traineeDto = new TraineeDto();
        traineeDto.setFirstName("John");
        traineeDto.setLastName("Doe");
        traineeDto.setIsActive(true);
        traineeDto.setTrainersUsernames(List.of("Jane.Doe"));
    }

    @Test
    void testToDto() {
        TraineeDto dto = traineeMapper.toDto(trainee);

        assertNotNull(dto);
        assertEquals(trainee.getUser().getFirstName(), dto.getFirstName());
        assertEquals(trainee.getUser().getLastName(), dto.getLastName());
        assertTrue(dto.getIsActive());
        assertEquals(1, dto.getTrainersUsernames().size());
        assertEquals("Jane.Doe", dto.getTrainersUsernames().get(0));
    }

    @Test
    void testToEntity() {
        when(trainerService.findTrainerByUsername("Jane.Doe")).thenReturn(trainer);

        Trainee entity = traineeMapper.toEntity(traineeDto, trainerService);

        assertNotNull(entity);
        assertEquals(traineeDto.getFirstName(), entity.getUser().getFirstName());
        assertEquals(traineeDto.getLastName(), entity.getUser().getLastName());
        assertTrue(entity.getUser().getIsActive());
        assertNotNull(entity.getTrainers());
        assertEquals(1, entity.getTrainers().size());
        assertTrue(entity.getTrainers().contains(trainer));
    }

    @Test
    void testMapTrainersToUsernames() {
        List<String> usernames = traineeMapper.mapTrainersToUsernames(trainee.getTrainers());

        assertNotNull(usernames);
        assertEquals(1, usernames.size());
        assertEquals("Jane.Doe", usernames.get(0));
    }

    @Test
    void testMapUsernamesToTrainers() {
        when(trainerService.findTrainerByUsername("Jane.Doe")).thenReturn(trainer);

        Set<Trainer> trainers = traineeMapper.mapUsernamesToTrainers(traineeDto.getTrainersUsernames(), trainerService);

        assertNotNull(trainers);
        assertEquals(1, trainers.size());
        assertTrue(trainers.contains(trainer));
    }
}
