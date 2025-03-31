package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.example.dao.TrainerDao;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerDaoImpl implements TrainerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Optional<Trainer> findByUsername(String username) {
        String jpql = "SELECT t FROM Trainer t WHERE t.user.username = :username";
        return entityManager.createQuery(jpql, Trainer.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    @Override
    public Optional<Trainer> saveTrainer(Trainer trainer) {
        try {
            if (trainer.getId() == null) {
                System.out.println("Saving trainer " + trainer);
                entityManager.persist(trainer);
                return Optional.of(trainer);
            } else {
                return Optional.of(entityManager.merge(trainer));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Trainer> findNotAssignedTrainers(String traineeUsername) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> query = cb.createQuery(Trainer.class);
        Root<Trainer> trainerRoot = query.from(Trainer.class);

        Subquery<Trainer> subquery = query.subquery(Trainer.class);
        Root<Trainee> traineeRoot = subquery.from(Trainee.class);
        Join<Trainee, Trainer> joinedTrainers = traineeRoot.join("trainers");
        subquery.select(joinedTrainers)
                .where(cb.equal(traineeRoot.get("user").get("username"), traineeUsername));

        query.select(trainerRoot)
                .where(cb.not(cb.in(trainerRoot).value(subquery)));

        return entityManager.createQuery(query).getResultList();
    }

}
