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

        //Student
        StudentService studentService = context.getBean(StudentService.class);

        Student student = studentService.saveStudent(
                new Student("Анна", "Иванова", "anna.test@example.com", LocalDate.now())
        );
        System.out.println("Создан студент: " + student);

        Student foundStudent = studentService.getById(student.getId());
        System.out.println("Найден студент по id: " + foundStudent);

        foundStudent.setLastName("Петрова");
        Student updatedStudent = studentService.updateStudent(foundStudent);
        System.out.println("Обновлён студент: " + updatedStudent);

        System.out.println("Все студенты: " + studentService.findAll());

        studentService.deleteStudent(student.getId());
        System.out.println("Студент удалён, попытка найти снова: " + studentService.getById(student.getId()));

        //Profile
        ProfileService profileService = context.getBean(ProfileService.class);

        Profile profile = profileService.saveProfile(
                new Profile("+79991234567", "Москва, ул. Ленина 1", "Люблю программирование")
        );
        System.out.println("Создан профиль: " + profile);

        Profile foundProfile = profileService.getById(profile.getId());
        System.out.println("Найден профиль по id: " + foundProfile);

        foundProfile.setAddress("Москва, ул. Пушкина 10");
        Profile updatedProfile = profileService.updateProfile(foundProfile);
        System.out.println("Обновлён профиль: " + updatedProfile);

        System.out.println("Все профили: " + profileService.findAll());

        profileService.deleteProfile(profile.getId());
        System.out.println("Профиль удалён, попытка найти снова: " + profileService.getById(profile.getId()));

        //Instructor
        InstructorService instructorService = context.getBean(InstructorService.class);

        Instructor instructor = instructorService.saveInstructor(
                new Instructor("Иван", "Петров", "Java")
        );
        System.out.println("Создан преподаватель: " + instructor);

        Instructor foundInstructor = instructorService.getById(instructor.getId());
        System.out.println("Найден преподаватель по id: " + foundInstructor);

        foundInstructor.setSpecialization("Java / Spring");
        Instructor updatedInstructor = instructorService.updateInstructor(foundInstructor);
        System.out.println("Обновлён преподаватель: " + updatedInstructor);

        System.out.println("Все преподаватели: " + instructorService.findAll());

        //Course
        CourseService courseService = context.getBean(CourseService.class);

        Course course = courseService.saveCourse(
                new Course("Java Core", "Основы Java", LocalDate.now())
        );
        System.out.println("Создан курс: " + course);

        Course foundCourse = courseService.getById(course.getId());
        System.out.println("Найден курс по id: " + foundCourse);

        foundCourse.setDescription("Основы Java и ООП");
        Course updatedCourse = courseService.updateCourse(foundCourse);
        System.out.println("Обновлён курс: " + updatedCourse);

        System.out.println("Все курсы: " + courseService.findAll());

        courseService.deleteCourse(course.getId());
        System.out.println("Курс удалён, попытка найти снова: " + courseService.getById(course.getId()));

        instructorService.deleteInstructor(instructor.getId());
        System.out.println("Преподаватель удалён");

        context.close();
    }
}