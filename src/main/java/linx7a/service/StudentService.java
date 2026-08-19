package linx7a.service;

import linx7a.TransactionHelper;
import linx7a.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public StudentService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Student saveStudent(Student student) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(student);
            return student;
        });
    }

    public void deleteStudent(Long id) {
        transactionHelper.executeInTransaction(session -> {
            Student studentForDelete = session.find(Student.class, id);
            session.remove(studentForDelete);
        });
    }

    public Student getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Student student = session.find(Student.class, id);
            return student;
        }
    }

    public List<Student> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Student> allStudents = session
                    .createQuery("SELECT s FROM Student s", Student.class)
                    .list();
            return allStudents;
        }
    }

    public Student updateStudent(Student student) {
        return transactionHelper.executeInTransaction(session -> {
            return session.merge(student);
        });
    }
}
