package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.example.dao.TrainingDao;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.User;
import org.example.model.enums.TrainingType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainingDaoImpl implements TrainingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Training> findAllTrainingsByTraineeUsernameCriteria(String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, TrainingType trainingType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> training = cq.from(Training.class);

        // З'єднання з Trainee через User
        Join<Training, Trainee> trainee = training.join("trainee");
        Join<Trainee, User> traineeUser = trainee.join("user");

        // З'єднання з Trainer через User
        Join<Training, Trainer> trainer = training.join("trainer");
        Join<Trainer, User> trainerUser = trainer.join("user");

        List<Predicate> predicates = new ArrayList<>();

        // Фільтруємо по username Trainee
        if (traineeUsername != null && !traineeUsername.isEmpty()) {
            predicates.add(cb.equal(traineeUser.get("username"), traineeUsername));
        }

        // Фільтруємо по датах
        if (fromDate != null && toDate != null) {
            predicates.add(cb.between(training.get("date"), fromDate, toDate));
        }

        // Фільтруємо по trainerUsername
        if (trainerUsername != null && !trainerUsername.isEmpty()) {
            predicates.add(cb.like(trainerUser.get("username"), "%" + trainerUsername + "%"));
        }

        // Фільтруємо по типу тренування
        if (trainingType != null) {
            predicates.add(cb.equal(training.get("trainingType"), trainingType));
        }

        // Додаємо всі умови до запиту
        cq.select(training).where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Training> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Training> findAllTrainingsByTrainerUsernameCriteria(String trainerUsername, LocalDate fromDate, LocalDate toDate, String traineeName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> training = cq.from(Training.class);

        // З'єднання з Trainee через User
        Join<Training, Trainee> trainee = training.join("trainee");
        Join<Trainee, User> traineeUser = trainee.join("user");

        // З'єднання з Trainer через User
        Join<Training, Trainer> trainer = training.join("trainer");
        Join<Trainer, User> trainerUser = trainer.join("user");

        List<Predicate> predicates = new ArrayList<>();

        // Фільтруємо по username Trainee
        if (trainerUsername != null && !trainerUsername.isEmpty()) {
            predicates.add(cb.equal(trainerUser.get("username"), trainerUsername));
        }

        // Фільтруємо по датах
        if (fromDate != null && toDate != null) {
            predicates.add(cb.between(training.get("date"), fromDate, toDate));
        }

        // Фільтруємо по trainerUsername
        if (traineeName != null && !traineeName.isEmpty()) {
            predicates.add(cb.like(trainerUser.get("username"), "%" + trainerUsername + "%"));
        }

        // Додаємо всі умови до запиту
        cq.select(training).where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Training> query = entityManager.createQuery(cq);
        return query.getResultList();
    }


    @Transactional
    @Override
    public Optional<Training> saveTraining(Training training) {
        Long id = training.getId();
        try {
            if (id == null) {
                entityManager.persist(training);
                return Optional.of(training);
            } else {
                return Optional.of(entityManager.merge(training));
            }
        }  catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
