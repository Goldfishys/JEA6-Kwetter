package Services;

import DAL.Interfaces.IProfile;
import models.Profile;

import javax.inject.Inject;
import javax.inject.Named;

@Named("ProfileServices")
public class ProfileServices {

    @Inject
    private IProfile profileRepo;

    //region constructors
    public ProfileServices() {
    }
    //endregion

    //region methods
    public Profile GetProfile(int userid) {
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
