package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IUser;
import models.Account;
import models.User;

public class UserRepoMock implements IUser {
    private Database database;

    public UserRepoMock(Database database) {
        this.database = database;
    }

    @Override
    public User LoadUser(int ID) {
        for (Account acc : database.accounts) {
            if (acc.getID() == ID) {
                return acc.getUser();
            }
        }
        return null;
    }

    @Override
    public void UpdateUsername(int ID, String username) {
        for (Account acc : database.accounts) {
            if (acc.getID() == ID) {
                acc.getUser().setUsername(username);
            }
        }
    }
}
