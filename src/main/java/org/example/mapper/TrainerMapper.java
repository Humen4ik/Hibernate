package org.example.mapper;

import org.example.dto.TrainerDto;
import org.example.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    @Mapping(source = "specialization.trainingType", target = "specialization")  // Маппінг поля specialization
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.isActive", target = "isActive")
    TrainerDto toDto(Trainer trainer);

    @Mapping(source = "specialization", target = "specialization.trainingType")  // Маппінг поля specialization
    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "isActive", target = "user.isActive")
    Trainer toEntity(TrainerDto trainerDto);

    default Set<Trainer> toEntityList(Set<TrainerDto> trainerDtos) {
        return trainerDtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    default List<TrainerDto> toDtoList(List<Trainer> trainers) {
        return trainers.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}