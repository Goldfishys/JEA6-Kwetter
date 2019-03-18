package DAL.Implementations.Database;

import DAL.Interfaces.IUser;
import models.Account;
import models.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements IUser {

    EntityManager em;

    public UserRepo() {
        em = Persistence.createEntityManagerFactory("KwetterHibernatePersistence").createEntityManager();
    }

    @Override
    public User GetUser(int ID) {
        return em.find(User.class, ID);
    }

    @Override
    public User UpdateUsername(int id, String username) {
        User user = em.find(User.class, id);

        em.getTransaction().begin();
        user.setUsername(username);
        em.persist(user);
        em.getTransaction().commit();

        return user;
    }

    @Override
    public ArrayList<User> GetFollowers(List<Account> followers) {
        ArrayList<User> users = new ArrayList<>();
        for(Account follower : followers){
            users.add(GetUser(follower.getID()));
        }
        return users;
    }

    public ArrayList<User> GetFollowing(List<Account> following) {
        //TODO find better way to get following
        ArrayList<User> users = new ArrayList<>();
        for(Account acc : following){
            users.add(GetUser(acc.getID()));
        }
        return users;
    }

    public void FollowUser(int idToFollow, Account Follower) {
        User user = em.find(User.class, idToFollow);

        em.getTransaction().begin();
        user.AddFollower(Follower);
        em.persist(user);
        em.getTransaction().commit();
    }

    public void UnFollowUser(int idToFollow, Account follower) {
        User user = em.find(User.class, idToFollow);

        em.getTransaction().begin();
        user.RemoveFollower(follower);
        em.persist(user);
        em.getTransaction().commit();
    }
}
