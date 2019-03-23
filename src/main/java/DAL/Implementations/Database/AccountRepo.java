package DAL.Implementations.Database;

import DAL.Interfaces.IAccount;
import models.Account;

import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import java.util.ArrayList;

@RequestScoped
public class AccountRepo implements IAccount {

    EntityManagerFactory emf;

    public AccountRepo() {
        emf = Persistence.createEntityManagerFactory("KwetterHibernatePersistence");
    }

    @Override
    public Account Register(Account account) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return account;
    }

    @Override
    public Account GetAccountByID(int ID) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Account.class, ID);
        } finally {
            em.close();
        }
    }

    @Override
    public ArrayList<Account> GetAccounts() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT new models.Account(a.id) from Account a", Account.class);
            return new ArrayList<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    public Account login(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("select a from Account a, User u where u.username = :username and a.password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password);
            return (Account) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }
}
