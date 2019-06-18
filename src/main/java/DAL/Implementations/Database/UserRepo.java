package DAL.Implementations.Database;

import DAL.Interfaces.IUser;
import models.Account;
import models.User;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        User user = em.find(User.class, ID);
        return user;
    }

    @Override
    public User UpdateUsername(int id, String username) {
        User user = em.find(User.class, id);
        user.setUsername(username);
        return user;
    }

    @Override
    public ArrayList<User> GetFollowers(List<Account> followers) {
        ArrayList<User> users = new ArrayList<>();
        for (Account follower : followers) {
            users.add(GetUser(follower.getID()));
        }
        return users;
    }

    public ArrayList<User> GetFollowing(List<Account> following) {
        //TODO find better way to get following
        ArrayList<User> users = new ArrayList<>();
        for (Account acc : following) {
            users.add(GetUser(acc.getID()));
        }
        return users;
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
