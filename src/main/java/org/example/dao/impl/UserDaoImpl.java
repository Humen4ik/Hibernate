package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.dao.UserDao;
import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Boolean existByUsernameAndPassword(String username, String password) {
        String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username AND u.password = :password";
        return entityManager.createQuery(jpql, Boolean.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }

    @Transactional
    @Override
    public void changePassword(String username, String newPassword) {
        String jpql = "UPDATE User u SET u.password = :password WHERE u.username = :username";
        entityManager.createQuery(jpql)
                .setParameter("username", username)
                .setParameter("password", newPassword)
                .executeUpdate();
    }

    @Transactional
    @Override
    public void activateUser(String username) {
        String jpql = "UPDATE User u SET u.isActive = true WHERE u.username = :username";
        entityManager.createQuery(jpql)
                .setParameter("username", username)
                .executeUpdate();
    }

    @Transactional
    @Override
    public void deactivateUser(String username) {
        String jpql = "UPDATE User u SET u.isActive = false WHERE u.username = :username";
        entityManager.createQuery(jpql)
                .setParameter("username", username)
                .executeUpdate();
    }

    @Transactional
    @Override
    public List<User> findAll() {
        String jpql = "SELECT u FROM User u";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }


}
