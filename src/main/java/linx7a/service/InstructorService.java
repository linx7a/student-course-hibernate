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
    /**
     * Загружает инструктора вместе со списком его курсов.
     *
     * Поле courseList помечено как @OneToOne(mappedBy = "instructor"), то есть это
     * LAZY-связь по умолчанию. Обычный getById() вернёт инструктора с "прокси"
     * вместо реального списка курсов: сам список физически не подгружается из базы,
     * пока к нему кто-то не обратится.
     *
     * Проблема в том, что как только сессия (Session) закрывается, Hibernate теряет
     * возможность дозагрузить эти данные. Если обратиться к courseList ПОСЛЕ закрытия
     * сессии, вылетит LazyInitializationException (это можно увидеть в Main, где
     * geById().getCourseList() падает именно по этой причине).
     *
     * Этот метод решает проблему: он явно "трогает" список (.size()) ПОКА сессия
     * ещё открыта, это заставляет Hibernate сходить в базу за реальными данными
     * прямо сейчас, а не откладывать загрузку на потом. После этого объект instructor
     * можно спокойно использовать и вне сессии, courseList уже содержит настоящие
     * данные, а не прокси-заглушку.
     */
    public Instructor getByIdWithCourses(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Instructor instructor = session.find(Instructor.class, id);
            instructor.getCourseList().size(); // "трогаем" список —> Hibernate реально загружает его СЕЙЧАС, пока сессия жива
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
