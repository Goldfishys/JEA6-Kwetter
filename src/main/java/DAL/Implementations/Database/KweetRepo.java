package DAL.Implementations.Database;

import DAL.Interfaces.IKweet;
import models.Kweet;

import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.TreeSet;

@Default
public class KweetRepo implements IKweet {

    EntityManager em;

    public KweetRepo() {
        em = Persistence.createEntityManagerFactory("KwetterHibernatePersistence").createEntityManager();
    }

    @Override
    public ArrayList<Kweet> SearchKweets(String searchTerm) {
        searchTerm = "%" + searchTerm + "%";
        Query query = em.createQuery("SELECT k from Kweet k where LOWER(k.text) like :searchTerm")
                .setParameter("searchTerm", searchTerm.toLowerCase());
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public ArrayList<Kweet> SearchMentions(String searchTerm) {
        return null;
    }

    @Override
    public TreeSet<Kweet> GetKweetsForAccount(int accountID) {
        Query query = em.createQuery("select k from Kweet k where k.author = :accountID")
                .setParameter("accountID", accountID);
        return new TreeSet<>(query.getResultList());
    }

    @Override
    public Kweet GetKweet(int KweetID) {
        return em.find(Kweet.class, KweetID);
    }

    @Override
    public boolean DeleteKweet(int kweetID) {
        Kweet kweet = em.find(Kweet.class, kweetID);
        try {
            em.getTransaction().begin();
            em.remove(kweet);
            em.getTransaction().commit();
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public Kweet PostKweet(Kweet kweet) {
        em.getTransaction().begin();
        em.persist(kweet);
        em.getTransaction().commit();
        return kweet;
    }

    @Override
    public Kweet UpdateKweet(Kweet kweet) {
        Kweet kweetem = em.find(Kweet.class, kweet.getID());

        em.getTransaction().begin();
        kweetem.Update(kweet);
        em.persist(kweetem);
        em.getTransaction().commit();

        return kweetem;
    }
}
