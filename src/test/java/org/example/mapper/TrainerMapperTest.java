package org.example.mapper;

import static org.junit.jupiter.api.Assertions.*;
import org.example.dto.TrainerDto;
import org.example.model.Trainer;
import org.example.model.User;
import org.example.model.TrainingTypeEntity;
import org.example.model.enums.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;


import java.util.List;
import java.util.Set;


class TrainerMapperTest {

    private TrainerMapper trainerMapper = Mappers.getMapper(TrainerMapper.class);

    private Trainer trainer;
    private TrainerDto trainerDto;

    @BeforeEach
    void setUp() {
        // Setting up data for testing
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setIsActive(true);

        TrainingTypeEntity specialization = new TrainingTypeEntity();
        specialization.setTrainingType(TrainingType.CARDIO);

        trainer = new Trainer();
        trainer.setUser(user);
        trainer.setSpecialization(specialization);

        trainerDto = new TrainerDto();
        trainerDto.setFirstName("John");
        trainerDto.setLastName("Doe");
        trainerDto.setIsActive(true);
        trainerDto.setSpecialization("CARDIO");
    }

    @Test
    void testToDto() {
        // Testing the mapping from Trainer to TrainerDto
        TrainerDto result = trainerMapper.toDto(trainer);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertTrue(result.getIsActive());
        assertEquals("CARDIO", result.getSpecialization());
    }

    @Test
    void testToEntity() {
        // Testing the mapping from TrainerDto to Trainer
        Trainer result = trainerMapper.toEntity(trainerDto);

        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals("John", result.getUser().getFirstName());
        assertEquals("Doe", result.getUser().getLastName());
        assertTrue(result.getUser().getIsActive());
        assertNotNull(result.getSpecialization());
        assertEquals(TrainingType.CARDIO, result.getSpecialization().getTrainingType());
    }

    @Test
    void testToEntityList() {
        // Testing the mapping from Set<TrainerDto> to Set<Trainer>
        Set<Trainer> trainers = trainerMapper.toEntityList(Set.of(trainerDto));

        assertNotNull(trainers);
        assertEquals(1, trainers.size());
        Trainer mappedTrainer = trainers.iterator().next();
        assertEquals("John", mappedTrainer.getUser().getFirstName());
        assertEquals(TrainingType.CARDIO, mappedTrainer.getSpecialization().getTrainingType());
    }

    @Test
    void testToDtoList() {
        // Testing the mapping from List<Trainer> to List<TrainerDto>
        List<TrainerDto> trainerDtos = trainerMapper.toDtoList(List.of(trainer));

        assertNotNull(trainerDtos);
        assertEquals(1, trainerDtos.size());
        TrainerDto mappedDto = trainerDtos.get(0);
        assertEquals("John", mappedDto.getFirstName());
        assertEquals("CARDIO", mappedDto.getSpecialization());
    }
}
