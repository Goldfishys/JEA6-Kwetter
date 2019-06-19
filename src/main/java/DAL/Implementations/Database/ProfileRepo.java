package DAL.Implementations.Database;

import DAL.Interfaces.IProfile;
import models.Profile;
import models.User;
import models.dtomodels.KweetDTO;
import models.dtomodels.ProfileDTO;

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

    public ProfileDTO GetProfile(int userid) {
        return  em.createQuery("select new models.dtomodels.ProfileDTO(u.profile.profilePicture, u.profile.bio, u.profile.location, u.profile.websiteURL, u.username ) from User u where u.id=:userid", ProfileDTO.class)
                .setParameter("userid", userid)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public Profile UpdateProfile(int userid, Profile profile) {
        Profile profile1 = em.find(User.class, userid).getProfile();
        profile1.UpdateProfile(profile);
        return profile1;
    }
}
