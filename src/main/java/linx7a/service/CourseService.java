package linx7a.service;

import linx7a.entity.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final SessionFactory sessionFactory;

    public CourseService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Course saveCourse(Course course) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(course);
            transaction.commit();
            return course;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteCourse(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Course course = session.find(Course.class, id);
            session.remove(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
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
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            course = session.merge(course);
            transaction.commit();
            return course;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
