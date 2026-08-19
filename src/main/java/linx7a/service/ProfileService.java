package linx7a.service;

import linx7a.TransactionHelper;
import linx7a.entity.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public ProfileService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Profile saveProfile(Profile profile) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(profile);
            return profile;
        });
    }

    public void deleteProfile(Long id) {
        transactionHelper.executeInTransaction(session -> {
            Profile profile = session.find(Profile.class, id);
            session.remove(profile);
        });
    }

    public Profile getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Profile profile = session.find(Profile.class, id);
            return profile;
        }
    }

    public List<Profile> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Profile> allProfiles = session
                    .createQuery("SELECT p FROM Profile p", Profile.class)
                    .list();
            return allProfiles;
        }
    }

    public Profile updateProfile(Profile profile) {
        return transactionHelper.executeInTransaction(session -> {
            return session.merge(profile);
        });
    }
}
