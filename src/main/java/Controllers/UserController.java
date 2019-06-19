package Controllers;

import Services.UserServices;
import models.User;
import models.dtomodels.UserDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserController {

    @Inject
    UserServices us;

    public User GetUser(int userID) {
        return us.GetUser(userID);
    }

    public User UpdateUsername(int userID, String username) {
        return us.UpdateUsername(userID, username);
    }

    public List<UserDTO> GetFollowers(int userID) {
        return us.GetFollowers(userID);
    }

    public List<UserDTO> GetFollowing(int id) {
        return us.GetFollowing(id);
    }

    public void FollowUser(int idToFollow, int idFollower) {
        us.FollowUser(idToFollow, idFollower);
    }

    public void UnFollowUser(int idToFollow, int idFollower) {
        us.UnFollowUser(idToFollow, idFollower);
    }
}
