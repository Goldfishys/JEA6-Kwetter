package DAL.Implementations.Database;

import DAL.Interfaces.IAccount;
import models.Account;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;

public class AccountRepo implements IAccount {

    EntityManager em;

    public AccountRepo(){
        em = Persistence.createEntityManagerFactory("KwetterHibernatePersistence").createEntityManager();
    }

    @Override
    public Account Register(Account account) {
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        return account;
    }

    @Override
    public Account GetAccountByID(int ID) {
        Account acc = em.find(Account.class, ID);
        return acc;
    }

    @Override
    public ArrayList<Account> GetAccounts() {
        Query query = em.createQuery("SELECT new models.Account(a.id) from Account a", Account.class);
        return new ArrayList<>(query.getResultList());
    }

    public Account login(String username, String password) {
        Query query = em.createQuery("select a from Account a, User u where u.username = :username and a.password = :password")
                .setParameter("username", username)
                .setParameter("password", password);

        try{
            return (Account) query.getSingleResult();
        }
        catch (NoResultException ex){
            return null;
        }
    }
}
