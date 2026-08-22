    package linx7a.entity;

    import jakarta.persistence.*;

    import java.time.LocalDate;
    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Table(name = "students")
    public class Student {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "first_name", nullable = false)
        private String firstName;
        @Column(name = "last_name", nullable = false)
        private String lastName;
        @Column(name = "email", unique = true, nullable = false)
        private String email;
        @Column(name = "enrollment_date")
        private LocalDate enrollmentDate;
        public Student() {
        }
        @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
        private Profile profile;

        @ManyToMany
        @JoinTable(
                name = "student_course",
                joinColumns = @JoinColumn(name = "student_id"),
                inverseJoinColumns = @JoinColumn(name = "course_id")
        )
        private Set<Course> courses = new HashSet<> ();

        public Student(String firstName, String lastName, String email, LocalDate enrollmentDate) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.enrollmentDate = enrollmentDate;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalDate getEnrollmentDate() {
            return enrollmentDate;
        }

        public void setEnrollmentDate(LocalDate enrollmentDate) {
            this.enrollmentDate = enrollmentDate;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public Set<Course> getCourses() {
            return courses;
        }

        public void setCourses(Set<Course> courses) {
            this.courses = courses;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", enrollmentDate=" + enrollmentDate +
                    '}';
        }
    }
