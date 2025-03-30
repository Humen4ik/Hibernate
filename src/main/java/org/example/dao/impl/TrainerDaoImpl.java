package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.dao.TrainerDao;
import org.example.model.Trainer;
import org.springframework.stereotype.Repository;

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
}
