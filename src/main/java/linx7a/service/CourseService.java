package linx7a.service;

import linx7a.TransactionHelper;
import linx7a.entity.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public CourseService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Course saveCourse(Course course) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(course);
            return course;
        });
    }

    public void deleteCourse(Long id) {
        transactionHelper.executeInTransaction(session -> {
            Course course = session.find(Course.class, id);
            session.remove(course);
        });
}

    public Course getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Course course = session.find(Course.class, id);
            return course;
        }
    }

    public List<Course> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Course> allCourses = session
                    .createQuery("SELECT c FROM Course c", Course.class)
                    .list();
            return allCourses;
        }
    }

    public Course updateCourse(Course course) {
        return transactionHelper.executeInTransaction(session -> {
            return session.merge(course);
        });
    }
}
