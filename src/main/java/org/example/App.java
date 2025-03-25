package org.example;

import org.example.conf.BaseConfig;
import org.example.dao.TraineeDao;
import org.example.model.Trainee;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BaseConfig.class);
        TraineeDao traineeDao = context.getBean(TraineeDao.class);
        User user = new User();
        user.setUsername("dmytro.dmytro");
        user.setPassword("pass");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setUser(user);
        trainee.setAddress("Dubrovitska");
        traineeDao.save(trainee);

        traineeDao.deactivateTrainee("Dmytro.Humeniuk");
        traineeDao.changePassword("Dmytro.Humeniuk", "abcdef");

        traineeDao.deleteByUsername("Dmytro.Humeniuk");
    }
}
