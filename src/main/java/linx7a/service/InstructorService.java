package linx7a.service;

import linx7a.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    private final SessionFactory sessionFactory;

    public InstructorService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Instructor saveInstructor(Instructor instructor) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(instructor);
        session.getTransaction().commit();
        session.close();
        return instructor;
    }

    public void deleteInstructor(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Instructor instructor = session.find(Instructor.class, id);
        session.remove(instructor);
        session.getTransaction().commit();
        session.close();
    }

    public Instructor getById(Long id) {
        Session session = sessionFactory.openSession();
        Instructor instructor = session.find(Instructor.class, id);
        session.close();
        return instructor;
    }
    public List<Instructor> findAll() {
        Session session = sessionFactory.openSession();
        List<Instructor> allInstructors = session
                .createQuery("SELECT i FROM Instructor i", Instructor.class)
                .list();
        session.close();
        return allInstructors;
    }

    public Instructor updateInstructor(Instructor instructor) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        instructor = session.merge(instructor);
        session.getTransaction().commit();
        session.close();
        return instructor;
    }
}
