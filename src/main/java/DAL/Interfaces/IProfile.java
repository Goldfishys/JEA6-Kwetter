package DAL.Interfaces;

import models.Profile;
import models.dtomodels.ProfileDTO;

public interface IProfile {
    ProfileDTO GetProfile(int userid);

    Profile UpdateProfile(int id, Profile profile);
}
