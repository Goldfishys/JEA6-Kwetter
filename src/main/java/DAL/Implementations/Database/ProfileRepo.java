package DAL.Implementations.Database;

import DAL.Interfaces.IProfile;
import models.Profile;
import models.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ProfileRepo implements IProfile {

    EntityManager em;

    public ProfileRepo() {
        em = Persistence.createEntityManagerFactory("KwetterHibernatePersistence").createEntityManager();
    }

    public Profile GetProfile(int userid) {
        return em.find(User.class, userid).getProfile();
    }

    public Profile UpdateProfile(int userid, Profile profile) {
        Profile profile1 = em.find(User.class, userid).getProfile();

        em.getTransaction().begin();
        profile1.UpdateProfile(profile);
        em.getTransaction().commit();

        return profile1;
    }
}
