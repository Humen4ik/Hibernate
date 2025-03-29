package org.example.mapper;

import org.example.dto.TraineeDto;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.TrainerService;
import org.mapstruct.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TrainerService.class})
public interface TraineeMapper {

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.isActive", target = "isActive")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "trainersUsernames", expression = "java(mapTrainersToUsernames(trainee.getTrainers()))")
    TraineeDto toDto(Trainee trainee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.isActive", source = "isActive")
    @Mapping(target = "user.id", ignore = true)
    @Mapping(target = "trainers", expression = "java(mapUsernamesToTrainers(traineeDto.getTrainersUsernames(), trainerService))")
    Trainee toEntity(TraineeDto traineeDto, @Context TrainerService trainerService);

    default List<String> mapTrainersToUsernames(Set<Trainer> trainers) {
        return trainers != null ? trainers.stream()
                .map(trainer -> trainer.getUser().getUsername())
                .collect(Collectors.toList()) : null;
    }

    default Set<Trainer> mapUsernamesToTrainers(List<String> usernames, TrainerService trainerService) {
        return usernames != null ? usernames.stream()
                .map(trainerService::findTrainerByUsername)
                .collect(Collectors.toSet()) : null;
    }
}
