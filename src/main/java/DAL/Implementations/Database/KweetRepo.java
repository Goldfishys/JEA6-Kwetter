package DAL.Implementations.Database;

import DAL.Interfaces.IKweet;
import models.DTOmodels.KweetDTO;
import models.Kweet;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Default
@RequestScoped
public class KweetRepo implements IKweet, Serializable {

    @PersistenceContext
    private EntityManager em;

    public KweetRepo() {
    }

    @Override
    public List<KweetDTO> SearchKweets(String searchTerm) {
        searchTerm = "%" + searchTerm + "%";
        Query query = em.createQuery("select new models.DTOmodels.KweetDTO(k.ID, k.text, k.author, u.username, k.created) from Kweet k join Account a on k.author=a.ID join User u on a.user=u.account where LOWER(k.text) like :searchTerm")
                .setParameter("searchTerm", searchTerm.toLowerCase());
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public List<KweetDTO> SearchMentions(String searchTerm) {
        return null;
    }

    @Override
    public SortedSet<KweetDTO> GetKweetsForAccount(int accountID) {
        Query query = em.createQuery("select new models.DTOmodels.KweetDTO(k.ID, k.text, k.author, u.username, k.created) from Kweet k join Account a on k.author=a.ID join User u on a.user=u.account where k.author=:accountID", KweetDTO.class)
                .setParameter("accountID", accountID);
        return new TreeSet<>(query.getResultList());

    }

    @Override
    public KweetDTO GetKweet(int KweetID) {
        return  em.createQuery("select new models.DTOmodels.KweetDTO(k.ID, k.text, k.author, u.username, k.created) from Kweet k join Account a on k.author=a.ID join User u on a.user=u.account where k.ID =:kweetid", KweetDTO.class)
                .setParameter("kweetid", KweetID)
                .getSingleResult();
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
