package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.dao.TraineeDao;
import org.example.model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TraineeDaoImpl implements TraineeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Trainee> findByUsername(String username) {
        String jpql = "SELECT t FROM Trainee t WHERE t.user.username = :username";
        try {
            return Optional.of(entityManager.createQuery(jpql, Trainee.class)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    @Override
    @Transactional
    public Optional<Trainee> saveTrainee(Trainee trainee) {
        try {
            if (trainee.getId() == null) {
                entityManager.persist(trainee);
                return Optional.of(trainee);
            } else {
                return Optional.of(entityManager.merge(trainee));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public void deleteByUsername(String username) {
        try {
            Trainee trainee = entityManager.createQuery(
                            "SELECT t FROM Trainee t WHERE t.user.username = :username", Trainee.class)
                    .setParameter("username", username)
                    .getSingleResult();
            entityManager.remove(trainee);
        } catch (NoResultException e) {
            System.out.println("Trainee with username " + username + " not found.");
        }
    }

}
