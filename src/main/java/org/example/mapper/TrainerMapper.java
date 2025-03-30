package org.example.mapper
        ;
import org.example.dto.TrainerDto;
import org.example.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}