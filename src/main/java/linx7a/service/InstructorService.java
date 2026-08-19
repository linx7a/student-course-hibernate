package linx7a.service;

import linx7a.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    private final SessionFactory sessionFactory;

    public InstructorService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Instructor saveInstructor(Instructor instructor) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(instructor);
            transaction.commit();
            return instructor;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteInstructor(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Instructor instructor = session.find(Instructor.class, id);
            session.remove(instructor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Instructor getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Instructor instructor = session.find(Instructor.class, id);
            return instructor;
        }
    }

    public List<Instructor> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Instructor> allInstructors = session
                    .createQuery("SELECT i FROM Instructor i", Instructor.class)
                    .list();
            return allInstructors;
        }
    }

    public Instructor updateInstructor(Instructor instructor) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            instructor = session.merge(instructor);
            transaction.commit();
            return instructor;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
