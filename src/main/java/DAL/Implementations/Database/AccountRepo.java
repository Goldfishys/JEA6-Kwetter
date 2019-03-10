package DAL.Implementations.Database;

import DAL.Interfaces.IAccount;
import models.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AccountRepo implements IAccount {

    EntityManagerFactory emfac;

    public AccountRepo(){
        emfac = Persistence.createEntityManagerFactory("Hibernate-Persistence");
    }

    @Override
    public void Register(Account account) {
        EntityManager em = emfac.createEntityManager();
        em.getTransaction().begin();

        em.persist(account);
        em.getTransaction().commit();

        em.close();
    }

    @Override
    public Account GetAccountByID(int ID) {
        return null;
    }
}
