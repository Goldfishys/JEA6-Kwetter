package DAL.Implementations.Database;

import DAL.Interfaces.IKweet;
import models.Kweet;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.TreeSet;

@RequestScoped
public class KweetRepo implements IKweet {

    EntityManagerFactory emf;

    public KweetRepo() {
        emf = Persistence.createEntityManagerFactory("KwetterHibernatePersistence");
    }

    @Override
    public ArrayList<Kweet> SearchKweets(String searchTerm) {
        EntityManager em = emf.createEntityManager();
        try {
            searchTerm = "%" + searchTerm + "%";
            Query query = em.createQuery("SELECT k from Kweet k where LOWER(k.text) like :searchTerm")
                    .setParameter("searchTerm", searchTerm.toLowerCase());
            return new ArrayList<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public ArrayList<Kweet> SearchMentions(String searchTerm) {
        return null;
    }

    @Override
    public TreeSet<Kweet> GetKweetsForAccount(int accountID) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("select k from Kweet k where k.author = :accountID")
                    .setParameter("accountID", accountID);
            return new TreeSet<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public Kweet GetKweet(int KweetID) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Kweet.class, KweetID);
        }finally {
            em.close();
        }
    }

    @Override
    public boolean DeleteKweet(int kweetID) {
        EntityManager em = emf.createEntityManager();
        Kweet kweet = em.find(Kweet.class, kweetID);
        try {
            em.getTransaction().begin();
            em.remove(kweet);
            em.getTransaction().commit();
        } catch (Exception ex) {
            return false;
        }finally {
            em.close();
        }

        return true;
    }

    @Override
    public Kweet PostKweet(Kweet kweet) {
        EntityManager em = emf.createEntityManager();
        try {
            if(!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.persist(kweet);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return kweet;
    }

    @Override
    public Kweet UpdateKweet(Kweet kweet) {
        EntityManager em = emf.createEntityManager();

        Kweet kweetem = em.find(Kweet.class, kweet.getID());

        try {
            if(!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            kweetem.Update(kweet);
            em.merge(kweetem);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return kweetem;
    }
}
