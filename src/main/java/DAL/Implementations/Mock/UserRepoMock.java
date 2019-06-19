package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IUser;
import models.Account;
import models.User;
import models.dtomodels.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserRepoMock implements IUser {
    private Database database;

    public UserRepoMock(Database database) {
        this.database = database;
    }

    @Override
    public User GetUser(int ID) {
        for (Account acc : database.accounts) {
            if (acc.getID() == ID) {
                return acc.getUser();
            }
        }
        return null;
    }

    @Override
    public User UpdateUsername(int ID, String username) {
        for (Account acc : database.accounts) {
            if (acc.getID() == ID) {
                acc.getUser().setUsername(username);
                return acc.getUser();
            }
        }
        return null;
    }

    @Override
    public List<UserDTO> GetFollowers(int id) {
        return null;
    }

    @Override
    public List<UserDTO> GetFollowing(int id)    {
        return null;
    }

    @Override
    public void FollowUser(int idToFollow, Account Follower) {

    }

    @Override
    public void UnFollowUser(int idToFollow, Account follower) {

    }
}
