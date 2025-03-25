package org.example.dao.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.TraineeDao;
import org.example.model.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TraineeDaoImpl implements TraineeDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(Trainee t) {
        Transaction tn = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            tn = session.beginTransaction();
            session.save(t);
            tn.commit();
        } catch (Exception e) {
            if (tn != null) {
                tn.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(Trainee t) {

    }

    @Override
    public void deleteByUsername(String username) {
        Transaction tn = null;
        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();

            session.createQuery("DELETE FROM Trainee t WHERE t.user.username = :username")
                    .setParameter("username", username)
                    .executeUpdate();

            tn.commit();
        } catch (Exception e) {
            if (tn != null) {
                tn.rollback();
            }
            e.printStackTrace();
        }
    }




    @Override
    public Optional<Trainee> getTraineeByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("SELECT t FROM Trainee t LEFT JOIN FETCH t.trainers WHERE t.user.username = :username", Trainee.class)
                    .setParameter("username", username)
                    .uniqueResultOptional();
        }
    }

    @Override
    public void activateTrainee(String username) {
        Transaction tn = null;
        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();
            session.createQuery(
                    "UPDATE User u SET u.isActive = true WHERE u.username = :username"
            ).setParameter("username", username)
                    .executeUpdate();
        } catch (Exception e) {
            if (tn != null) {
                tn.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deactivateTrainee(String username) {
        Transaction tn = null;
        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();
            session.createQuery(
                            "UPDATE User u SET u.isActive = false WHERE u.username = :username"
                    ).setParameter("username", username)
                    .executeUpdate();
            tn.commit();
        } catch (Exception e) {
            if (tn != null) {
                tn.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void changePassword(String username, String password) {
        Transaction tn = null;
        try (Session session = sessionFactory.openSession()) {
            tn = session.beginTransaction();
            session.createQuery("UPDATE User u SET u.password = :password WHERE u.username = :username")
                    .setParameter("password", password)
                    .setParameter("username", username)
                            .executeUpdate();
            tn.commit();
        } catch (Exception e) {
            if (tn != null) {
                tn.rollback();
            }
            e.printStackTrace();
        }
    }
}
