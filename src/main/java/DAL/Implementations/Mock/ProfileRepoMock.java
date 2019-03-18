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
    public Profile GetProfile(int id) {
        for(Account acc : database.accounts){
            if(acc.getID() == id) return acc.getUser().getProfile();
        }
        return null;
    }

    @Override
    public Profile UpdateProfile(int id, Profile profile1) {
        Profile p = null;
        for(Account acc : database.accounts){
            if(acc.getID() == id){
                Profile profile = acc.getUser().getProfile();
                profile.UpdateProfile(profile1);
                p = profile;
            }
        }
        return p;
    }
}
