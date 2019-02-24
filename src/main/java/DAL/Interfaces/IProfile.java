package DAL.Interfaces;

import models.Profile;

public interface IProfile {
    Profile LoadProfileForAccount(int id);

    void UpdateProfile(int id, String bio, String location, String websiteURL, String profilePicture);

}
