package org.example;

import org.example.conf.BaseConfig;
import org.example.dao.TraineeDao;
import org.example.dao.TrainerDao;
import org.example.dto.TraineeDto;
import org.example.dto.TrainerDto;
import org.example.facade.TraineeFacade;
import org.example.facade.TrainerFacade;
import org.example.mapper.TraineeMapper;
import org.example.mapper.TrainerMapper;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.service.TraineeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BaseConfig.class);

        TrainerFacade trainerFacade = context.getBean(TrainerFacade.class);
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName("Oksana");
        trainerDto.setLastName("San");
        trainerDto.setIsActive(false);
        trainerDto.setSpecialization("CARDIO");
        trainerFacade.updateTrainer(trainerDto, "Andrii.Melnyk");

        context.close();
    }
}
