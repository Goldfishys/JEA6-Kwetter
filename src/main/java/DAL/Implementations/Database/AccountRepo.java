package DAL.Implementations.Database;

import DAL.Interfaces.IAccount;
import models.Account;
import models.dtomodels.AccountDTO;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

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
    public List<AccountDTO> GetAccounts() {
        return em.createQuery("select new models.dtomodels.AccountDTO(a.ID, u.username) from Account a join User u on a.user=u.account", AccountDTO.class)
                .getResultList();
    }

    @Override
    public Account login(String username, String password) {
        return em.createQuery("select a from Account a left join User u on a.ID = u.account.id where u.username = :username and a.password = :password", Account.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
