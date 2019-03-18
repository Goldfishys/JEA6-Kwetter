package DAL.Interfaces;

import models.Profile;

public interface IProfile {
    Profile GetProfile(int userid);

    Profile UpdateProfile(int id, Profile profile);
}
