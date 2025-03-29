package org.example;

import org.example.conf.BaseConfig;
import org.example.dao.TraineeDao;
import org.example.dao.TrainerDao;
import org.example.dto.TraineeDto;
import org.example.facade.TraineeFacade;
import org.example.mapper.TraineeMapper;
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

        TraineeDto dto = new TraineeDto();
        dto.setFirstName("Oksana");
        dto.setLastName("Oksana");
        dto.setAddress("dubr");
        dto.setIsActive(true);
        List<String> trainersNames = new ArrayList<>();
        trainersNames.add("Andrii.Melnyk");
        trainersNames.add("Oleksii.Bondarenko");
        dto.setTrainersUsernames(trainersNames);

        TraineeFacade traineeFacade = context.getBean(TraineeFacade.class);
        traineeFacade.deleteTraineeByUsername("Dmytro.Humeniuk");

        context.close();
    }
}
