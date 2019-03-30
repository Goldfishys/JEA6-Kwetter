package DAL.Implementations.Database;

import DAL.Interfaces.IKweet;
import models.Kweet;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

@RequestScoped
public class KweetRepo implements IKweet, Serializable {

    @PersistenceContext
    private EntityManager em;

    public KweetRepo() {
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
        System.out.println("got here!");
        System.out.println("em: " + em);
        Query query = em.createQuery("select k from Kweet k where k.author = :accountID")
                .setParameter("accountID", accountID);
        System.out.println("query: " + query);
        return new TreeSet<>(query.getResultList());

    }

    @Override
    public Kweet GetKweet(int KweetID) {
        return em.find(Kweet.class, KweetID);
    }

    @Override
    public boolean DeleteKweet(int kweetID) {
        Kweet kweet = em.find(Kweet.class, kweetID);
        em.remove(kweet);
        return true;
    }

    @Override
    public Kweet PostKweet(Kweet kweet) {
        em.persist(kweet);
        return kweet;
    }

    @Override
    public Kweet UpdateKweet(Kweet kweet) {
        Kweet kweetem = em.find(Kweet.class, kweet.getID());
        kweetem.Update(kweet);
        em.merge(kweetem);
        return kweetem;
    }
}
