package org.example;

import org.example.conf.BaseConfig;
import org.example.dto.TrainingDto;
import org.example.facade.TraineeFacade;
import org.example.facade.TrainerFacade;
import org.example.facade.TrainingFacade;
import org.example.service.AuthService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BaseConfig.class);

        TrainingFacade trainingFacade = context.getBean(TrainingFacade.class);
        TrainerFacade trainerFacade = context.getBean(TrainerFacade.class);
        TraineeFacade traineeFacade = context.getBean(TraineeFacade.class);

//        TraineeDto traineeDto = traineeFacade.findTraineeByUsername("Dmytro.Humeniuk");
//        TrainerDto trainerDto = trainerFacade.findTrainerByUsername("Oleksii.Bondarenko");
        TrainingDto trainingDto = new TrainingDto("Test Training", "Oleksii.Bondarenko", "Dmytro.Humeniuk", "2025-03-30", 85L, "BOXING");
//        trainingFacade.saveTraining(trainingDto);

        AuthService authService = context.getBean(AuthService.class);
        if (!authService.authenticate("Dmytro.Humeniuk", "pass123")) {
            System.out.println("Authentication failed!");
            return;
        }
//        System.out.println(traineeFacade.findTraineeByUsername("Dmytro.Humeniuk"));
        System.out.println(traineeFacade.getTraineeActivityStatus("Dmytro.Humeniuk"));

        context.close();
    }
}
