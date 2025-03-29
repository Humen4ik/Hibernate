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
}
