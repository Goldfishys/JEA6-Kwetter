package Services;

import DAL.Implementations.Database.UserRepo;
import models.Account;
import models.User;

import javax.inject.Inject;
import java.util.ArrayList;

public class UserServices {

    @Inject
    UserRepo userRepo;

    public User GetUser(int userID) {
        return userRepo.GetUser(userID);
    }

    public User UpdateUsername(int userID, String username) {
        if(username != null && username != "") {
            return userRepo.UpdateUsername(userID, username);
        }
        return null;
    }

    public ArrayList<User> GetFollowers(int userID) {
        User user = userRepo.GetUser(userID);
        return userRepo.GetFollowers(user.getFollowers());
    }

    public ArrayList<User> GetFollowing(int id) {
        User user = userRepo.GetUser(id);
        return userRepo.GetFollowing(user.getFollowing());
    }

    public void FollowUser(int idToFollow, int idFollower) {
        Account follower = GetUser(idFollower).getAccount();
        if(follower != null){
            userRepo.FollowUser(idToFollow, follower);
        }
    }

    public void UnFollowUser(int idToFollow, int idFollower) {
        Account follower = GetUser(idFollower).getAccount();
        if(follower != null){
            userRepo.UnFollowUser(idToFollow, follower);
        }
    }
}
