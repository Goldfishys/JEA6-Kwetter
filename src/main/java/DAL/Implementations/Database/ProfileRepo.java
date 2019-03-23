package DAL.Implementations.Database;

import DAL.Interfaces.IProfile;
import models.Profile;
import models.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RequestScoped
public class ProfileRepo implements IProfile {

    EntityManagerFactory emf;

    public ProfileRepo() {
        emf = Persistence.createEntityManagerFactory("KwetterHibernatePersistence");
    }

    public Profile GetProfile(int userid) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, userid).getProfile();
        } finally {
            em.close();
        }
    }

    public Profile UpdateProfile(int userid, Profile profile) {
        EntityManager em = emf.createEntityManager();
        try {
            Profile profile1 = em.find(User.class, userid).getProfile();

            em.getTransaction().begin();
            profile1.UpdateProfile(profile);
            em.getTransaction().commit();

            return profile1;
        }
        finally {
            em.close();
        }
    }
}
