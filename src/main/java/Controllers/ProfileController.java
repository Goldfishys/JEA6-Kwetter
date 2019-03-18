package Controllers;

import Services.ProfileServices;
import models.Profile;

import javax.inject.Inject;

public class ProfileController {

    @Inject
    private ProfileServices ps;


    public Profile GetProfile(int userid){
        return ps.GetProfile(userid);
    }

    public Profile UpdateProfile(int userid, Profile profile) {
        ps.UpdateProfile(userid, profile);
        return profile;
    }
}
