package linx7a.service;

import linx7a.TransactionHelper;
import linx7a.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public InstructorService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Instructor saveInstructor(Instructor instructor) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(instructor);
            return instructor;
        });
    }

    public void deleteInstructor(Long id) {
        transactionHelper.executeInTransaction(session -> {
            Instructor instructor = session.find(Instructor.class, id);
            session.remove(instructor);
        });
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
        return transactionHelper.executeInTransaction(session -> {
            return session.merge(instructor);
        });
    }
}
