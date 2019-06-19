package DAL.Interfaces;

import models.Account;
import models.User;
import models.dtomodels.UserDTO;

import java.util.ArrayList;
import java.util.List;

public interface IUser {

    User GetUser(int ID);
    User UpdateUsername(int id, String username);
    List<UserDTO> GetFollowers(int id);
    List<UserDTO> GetFollowing(int id);
    void FollowUser(int idToFollow, Account Follower);
    void UnFollowUser(int idToFollow, Account follower);
}
