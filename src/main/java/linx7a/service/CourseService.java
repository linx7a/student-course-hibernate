    package linx7a.service;

    import linx7a.entity.Course;
    import org.hibernate.Session;
    import org.hibernate.SessionFactory;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class CourseService {
        private final SessionFactory sessionFactory;

        public CourseService(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }

        public Course saveCourse(Course course) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(course);
            session.getTransaction().commit();
            session.close();
            return course;
        }

        public void deleteCourse(Long id) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Course course = session.find(Course.class, id);
            session.remove(course);
            session.getTransaction().commit();
            session.close();
        }

        public Course getById(Long id) {
            Session session = sessionFactory.openSession();
            Course course = session.find(Course.class, id);
            session.close();
            return course;
        }

        public List<Course> findAll() {
            Session session = sessionFactory.openSession();
            List<Course> allCourses = session
                    .createQuery("SELECT c FROM Course c", Course.class)
                    .list();
            session.close();
            return allCourses;
        }

        public Course updateCourse(Course course) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            course = session.merge(course);
            session.getTransaction().commit();
            session.close();
            return course;
        }
    }
