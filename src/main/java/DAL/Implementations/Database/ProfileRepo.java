package DAL.Implementations.Database;

import DAL.Interfaces.IProfile;
import models.Profile;
import models.User;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Default
@RequestScoped
public class ProfileRepo implements IProfile {

    @PersistenceContext
    private EntityManager em;

    public ProfileRepo() {
    }

    public Profile GetProfile(int userid) {
        return em.find(User.class, userid).getProfile();
    }

    public Profile UpdateProfile(int userid, Profile profile) {
        Profile profile1 = em.find(User.class, userid).getProfile();
        profile1.UpdateProfile(profile);
        return profile1;
    }
}
