package DAL.Implementations.Database;

import DAL.Interfaces.IUser;
import models.Account;
import models.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class UserRepo implements IUser {

    EntityManagerFactory emf;

    public UserRepo() {
        emf = Persistence.createEntityManagerFactory("KwetterHibernatePersistence");
    }

    @Override
    public User GetUser(int ID) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, ID);
        }
        finally {
            em.close();
        }
    }

    @Override
    public User UpdateUsername(int id, String username) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, id);

            em.getTransaction().begin();
            user.setUsername(username);
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } finally {
            em.close();
        }
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
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, idToFollow);

            em.getTransaction().begin();
            user.AddFollower(Follower);
            em.persist(user);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }

    public void UnFollowUser(int idToFollow, Account follower) {
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("found em...");
            User user = em.find(User.class, idToFollow);

            em.getTransaction().begin();
            user.RemoveFollower(follower);
            em.persist(user);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }
}
