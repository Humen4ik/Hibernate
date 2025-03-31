package org.example.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.example.dto.TrainingDto;
import org.example.model.*;
import org.example.model.enums.TrainingType;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingTypeEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainingMapperTest {

    private TrainingMapper trainingMapper;

    private TraineeService traineeService;
    private TrainerService trainerService;
    private TrainingTypeEntityService trainingTypeEntityService;

    private Training training;
    private TrainingDto trainingDto;

    @BeforeEach
    void setUp() {
        traineeService = mock(TraineeService.class);
        trainerService = mock(TrainerService.class);
        trainingTypeEntityService = mock(TrainingTypeEntityService.class);

        trainingMapper = Mappers.getMapper(TrainingMapper.class);

        // Set up test data
        Trainee trainee = new Trainee();
        trainee.setUser(new User());
        trainee.getUser().setUsername("trainee1");

        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        trainer.getUser().setUsername("trainer1");

        TrainingTypeEntity trainingType = new TrainingTypeEntity();
        trainingType.setTrainingType(TrainingType.CARDIO);

        training = new Training();
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingDate(new Date());
        training.setDuration(60);
        training.setTrainingType(trainingType);

        trainingDto = new TrainingDto();
        trainingDto.setTrainerUsername("trainer1");
        trainingDto.setTraineeUsername("trainee1");
        trainingDto.setDateTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        trainingDto.setDurationMinutes(60L);  // Long
        trainingDto.setType("CARDIO");
    }

    @Test
    void testToDto() {
        when(traineeService.findTraineeByUsername("trainee1")).thenReturn(training.getTrainee());
        when(trainerService.findTrainerByUsername("trainer1")).thenReturn(training.getTrainer());
        when(trainingTypeEntityService.findTrainingTypeByTrainingType(TrainingType.CARDIO))
                .thenReturn(training.getTrainingType());

        TrainingDto result = trainingMapper.toDto(training);

        assertNotNull(result);
        assertEquals("trainer1", result.getTrainerUsername());
        assertEquals("trainee1", result.getTraineeUsername());

        assertEquals(60, result.getDurationMinutes());
        assertEquals("CARDIO", result.getType());
    }

    @Test
    void testToEntity() {
        // Mock the behavior of services
        when(traineeService.findTraineeByUsername("trainee1")).thenReturn(training.getTrainee());
        when(trainerService.findTrainerByUsername("trainer1")).thenReturn(training.getTrainer());
        when(trainingTypeEntityService.findTrainingTypeByTrainingType(TrainingType.CARDIO))
                .thenReturn(training.getTrainingType());

        Training result = trainingMapper.toEntity(trainingDto, traineeService, trainerService, trainingTypeEntityService);

        assertNotNull(result);
        assertNotNull(result.getTrainer());
        assertNotNull(result.getTrainee());
        assertEquals("trainer1", result.getTrainer().getUser().getUsername());
        assertEquals("trainee1", result.getTrainee().getUser().getUsername());
        assertEquals(60, result.getDuration());
        assertEquals(TrainingType.CARDIO, result.getTrainingType().getTrainingType());
    }

    @Test
    void testToDtoList() {
        List<TrainingDto> result = trainingMapper.toDtoList(List.of(training));

        assertNotNull(result);
        assertEquals(1, result.size());
        TrainingDto dto = result.get(0);
        assertEquals("trainer1", dto.getTrainerUsername());
        assertEquals("trainee1", dto.getTraineeUsername());
        assertEquals(60, dto.getDurationMinutes());
        assertEquals("CARDIO", dto.getType());
    }

    @Test
    void testMapTrainee() {
        when(traineeService.findTraineeByUsername("trainee1")).thenReturn(training.getTrainee());

        Trainee result = trainingMapper.mapTrainee("trainee1", traineeService);

        assertNotNull(result);
        assertEquals("trainee1", result.getUser().getUsername());
    }

    @Test
    void testMapTrainer() {
        when(trainerService.findTrainerByUsername("trainer1")).thenReturn(training.getTrainer());

        Trainer result = trainingMapper.mapTrainer("trainer1", trainerService);

        assertNotNull(result);
        assertEquals("trainer1", result.getUser().getUsername());
    }

    @Test
    void testMapLocalDateToString() {
        String result = trainingMapper.mapLocalDateToString(LocalDate.now());

        assertNotNull(result);
        assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), result);
    }

    @Test
    void testMapStringToLocalDate() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate result = trainingMapper.mapStringToLocalDate(dateStr);

        assertNotNull(result);
        assertEquals(LocalDate.now(), result);
    }
}
