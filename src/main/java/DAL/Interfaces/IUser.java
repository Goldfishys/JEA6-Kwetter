package DAL.Interfaces;

import models.Account;
import models.User;

import java.util.ArrayList;
import java.util.List;

public interface IUser {

    User GetUser(int ID);
    User UpdateUsername(int id, String username);
    ArrayList<User> GetFollowers(List<Account> followers);
    ArrayList<User> GetFollowing(List<Account> followers);
    void FollowUser(int idToFollow, Account Follower);
    void UnFollowUser(int idToFollow, Account follower);
}
