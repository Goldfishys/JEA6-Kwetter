package Services;

import DAL.Interfaces.IUser;
import models.Account;
import models.User;
import models.dtomodels.UserDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class UserServices {

    @Inject
    IUser userRepo;

    public User GetUser(int userID) {
        return userRepo.GetUser(userID);
    }

    public User UpdateUsername(int userID, String username) {
        if(username != null && username != "") {
            return userRepo.UpdateUsername(userID, username);
        }
        return null;
    }

    public List<UserDTO> GetFollowers(int userID) {
        return userRepo.GetFollowers(userID);
    }

    public List<UserDTO> GetFollowing(int id) {
        return userRepo.GetFollowing(id);
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
            System.out.println("found the follower");
            userRepo.UnFollowUser(idToFollow, follower);
        }
    }
}
