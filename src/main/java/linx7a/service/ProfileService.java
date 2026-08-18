package linx7a.service;

import linx7a.entity.Course;
import linx7a.entity.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final SessionFactory sessionFactory;

    public ProfileService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Profile saveProfile(Profile profile) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(profile);
        session.getTransaction().commit();
        session.close();
        return profile;
    }

    public void deleteProfile(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Profile profile = session.find(Profile.class, id);
        session.remove(profile);
        session.getTransaction().commit();
        session.close();
    }

    public Profile getById(Long id) {
        Session session = sessionFactory.openSession();
        Profile profile = session.find(Profile.class, id);
        session.close();
        return profile;
    }

    public List<Profile> findAll() {
        Session session = sessionFactory.openSession();
        List<Profile> allProfiles = session
                .createQuery("SELECT p FROM Profile p", Profile.class)
                .list();
        session.close();
        return allProfiles;
    }

    public Profile updateProfile(Profile profile) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        profile = session.merge(profile);
        session.getTransaction().commit();
        session.close();
        return profile;
    }
}
