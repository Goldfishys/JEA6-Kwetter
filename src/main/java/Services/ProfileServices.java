package Services;

import DAL.Interfaces.IProfile;
import models.Profile;
import models.dtomodels.ProfileDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ProfileServices {

    @Inject
    private IProfile profileRepo;

    //region constructors
    public ProfileServices() {
    }
    //endregion

    //region methods
    public ProfileDTO GetProfile(int userid) {
        return profileRepo.GetProfile(userid);
    }

    public Profile UpdateProfile(int userid, Profile profile) {
        if(profile.IsValid()){
            return profileRepo.UpdateProfile(userid, profile);
        }
        return null;
    }
    //endregion
}
