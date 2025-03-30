package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.dao.TrainingTypeDao;
import org.example.model.TrainingTypeEntity;
import org.example.model.enums.TrainingType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Optional<TrainingTypeEntity> findTrainingTypeByName(TrainingType trainingType) {
        String jpql = "SELECT t FROM TrainingTypeEntity t WHERE t.trainingType = :trainingType";
        return entityManager.createQuery(jpql, TrainingTypeEntity.class).setParameter("trainingType", trainingType)
                .getResultStream().findFirst();
    }
}
