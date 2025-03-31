package org.example.mapper;

import org.example.dto.TrainingDto;
import org.example.model.enums.TrainingType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingTypeEntityService;
import org.example.model.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrainingMapper {
    TrainingMapper INSTANCE = Mappers.getMapper(TrainingMapper.class);

    @Mapping(source = "trainer.user.username", target = "trainerUsername")
    @Mapping(source = "trainee.user.username", target = "traineeUsername")
    @Mapping(source = "trainingDate", target = "dateTime", qualifiedByName = "mapLocalDateToString")
    @Mapping(source = "duration", target = "durationMinutes")
    @Mapping(source = "trainingType.trainingType", target = "type")
    TrainingDto toDto(Training training);

    @Mapping(source = "dto.dateTime", target = "trainingDate", qualifiedByName = "mapStringToLocalDate")
    @Mapping(source = "dto.durationMinutes", target = "duration")
    @Mapping(source = "dto.traineeUsername", target = "trainee", qualifiedByName = "mapTrainee")
    @Mapping(source = "dto.trainerUsername", target = "trainer", qualifiedByName = "mapTrainer")
    @Mapping(source = "dto.type", target = "trainingType", qualifiedByName = "mapTrainingType")
    Training toEntity(TrainingDto dto, @Context TraineeService traineeService, @Context TrainerService trainerService, @Context TrainingTypeEntityService trainingTypeEntityService);

    @Named("mapTrainee")
    default Trainee mapTrainee(String username, @Context TraineeService traineeService) {
        return traineeService.findTraineeByUsername(username);
    }

    default List<TrainingDto> toDtoList(List<Training> trainings) {
        return trainings.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Named("mapTrainer")
    default Trainer mapTrainer(String username, @Context TrainerService trainerService) {
        return trainerService.findTrainerByUsername(username);
    }

    @Named("mapTrainingType")
    default TrainingTypeEntity mapTrainingType(String type, @Context TrainingTypeEntityService trainingTypeEntityService) {
        return trainingTypeEntityService.findTrainingTypeByTrainingType(TrainingType.valueOf(type));
    }

    @Named("mapLocalDateToString")
    default String mapLocalDateToString(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    @Named("mapStringToLocalDate")
    default LocalDate mapStringToLocalDate(String date) {
        return date != null ? LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }
}
