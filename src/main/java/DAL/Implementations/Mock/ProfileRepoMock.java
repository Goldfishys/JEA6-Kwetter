package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IProfile;
import models.Account;
import models.Profile;

public class ProfileRepoMock implements IProfile {
    private Database database;

    public ProfileRepoMock(Database database){
        this.database = database;
    }

    @Override
    public Profile LoadProfileForAccount(int id) {
        for(Account acc : database.accounts){
            if(acc.getID() == id) return acc.getUser().getProfile();
        }
        return null;
    }

    @Override
    public void UpdateProfile(int id, String bio, String location, String websiteURL, String profilePicture) {
        for(Account acc : database.accounts){
            if(acc.getID() == id){
                Profile profile = acc.getUser().getProfile();
                profile.UpdateProfile(bio, location, websiteURL, profilePicture);
            }
        }
    }
}
