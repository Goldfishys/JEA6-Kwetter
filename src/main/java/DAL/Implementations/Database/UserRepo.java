package DAL.Implementations.Database;

import DAL.Interfaces.IUser;
import models.Account;
import models.User;
import models.dtomodels.UserDTO;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Default
@RequestScoped
public class UserRepo implements IUser {

    @PersistenceContext
    private EntityManager em;

    public UserRepo() {
    }

    @Override
    public User GetUser(int ID) {
        return em.find(User.class, ID);
    }

    @Override
    public User UpdateUsername(int id, String username) {
        User user = em.find(User.class, id);
        user.setUsername(username);
        return user;
    }

    @Override
    public List<UserDTO> GetFollowers(int userID) {
        User usr = em.find(User.class, userID);
        List<UserDTO> followers = new ArrayList<>();
        if(usr != null) {
            for (Account acc : usr.getFollowers()) {
                followers.add(new UserDTO(acc.getUser().getId(), acc.getUser().getUsername()));
            }
        }
        return followers;
    }

    public List<UserDTO> GetFollowing(int id) {
        User usr = em.find(User.class, id);
        List<UserDTO> following = new ArrayList<>();
        if(usr != null) {
            for (Account acc : usr.getFollowing()) {
                following.add(new UserDTO(acc.getUser().getId(), acc.getUser().getUsername()));
            }
        }
        return following;
    }

    public void FollowUser(int idToFollow, Account Follower) {
        User user = em.find(User.class, idToFollow);
        user.AddFollower(Follower);
    }

    public void UnFollowUser(int idToFollow, Account follower) {
        User user = em.find(User.class, idToFollow);
        user.RemoveFollower(follower);
    }
}
