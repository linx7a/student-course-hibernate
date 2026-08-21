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

        InstructorService instructorService = context.getBean(InstructorService.class);
        CourseService courseService = context.getBean(CourseService.class);

        Instructor instructor = instructorService.saveInstructor(
                new Instructor("Иван", "Петров", "Java")
        );
        System.out.println("Создан преподаватель: " + instructor);

        Course course = courseService.saveCourse(
                new Course("Java Core", "Основы Java", LocalDate.now(), instructor)
        );
        System.out.println("Создан курс: " + course);

        Course foundCourse = courseService.getById(course.getId());
        System.out.println("У курса преподаватель: " + foundCourse.getInstructor());
    }
}