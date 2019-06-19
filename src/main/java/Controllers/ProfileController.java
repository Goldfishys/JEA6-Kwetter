package Controllers;

import Services.ProfileServices;
import models.Profile;
import models.dtomodels.ProfileDTO;


import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ProfileController {

    @Inject
    private ProfileServices ps;

    public ProfileDTO GetProfile(int userid){
        return ps.GetProfile(userid);
    }

    public Profile UpdateProfile(int userid, Profile profile) {
        ps.UpdateProfile(userid, profile);
        return profile;
    }
}
