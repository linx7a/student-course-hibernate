package linx7a;

import linx7a.entity.Course;
import linx7a.entity.Instructor;
import linx7a.entity.Profile;
import linx7a.entity.Student;
import linx7a.service.CourseService;
import linx7a.service.InstructorService;
import linx7a.service.ProfileService;
import linx7a.service.StudentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        StudentService studentService = context.getBean(StudentService.class);
        ProfileService profileService = context.getBean(ProfileService.class);

        Student student = new Student("Пельмень", "Огурцов", "pelmen" + System.currentTimeMillis() + "@example.com", LocalDate.now());
        Profile profile = new Profile("+7999" + (System.currentTimeMillis() % 10000000), "Москва, диван у окна", "Профессионально прокрастинирую", student);

        student.setProfile(profile);
        profile.setStudent(student);

        Student savedStudent = studentService.saveStudent(student);
        System.out.println("Студент сохранен: " + savedStudent);
        System.out.println("Профиль каскадом: " + savedStudent.getProfile());

        Student found = studentService.getById(savedStudent.getId());
        System.out.println("Найден студент с профилем: " + found.getProfile());

    }
}