package DAL.Implementations.Database;

import DAL.Interfaces.IAccount;
import models.Account;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;

@Default
@RequestScoped
public class AccountRepo implements IAccount, Serializable {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public AccountRepo() {
    }

    @Override
    public Account Register(Account account) {
        em.persist(account);
        return account;
    }

    @Override
    public Account GetAccountByID(int ID) {
        return em.find(Account.class, ID);
    }

    @Override
    public ArrayList<Account> GetAccounts() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> rootEntry = cq.from(Account.class);
        CriteriaQuery<Account> all = cq.select(rootEntry);

        TypedQuery<Account> allQuery = em.createQuery(all);
        return (ArrayList<Account>) allQuery.getResultList();
    }

    public Account login(String username, String password) {
        Query query = em.createQuery("select a from Account a, User u where u.username = :username and a.password = :password")
                .setParameter("username", username)
                .setParameter("password", password);
        return (Account) query.getSingleResult();
    }
}
