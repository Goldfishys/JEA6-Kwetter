package DAL.Implementations.Database;

import DAL.Interfaces.IRole;
import models.Account;
import models.Role;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Default
@RequestScoped
public class RoleRepo implements IRole {

    @PersistenceContext
    private EntityManager em;

    public RoleRepo() {
    }

    @Override
    public List<Role> getAllRoles() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Role> cq = cb.createQuery(Role.class);
        Root<Role> rootEntry = cq.from(Role.class);
        CriteriaQuery<Role> all = cq.select(rootEntry);

        TypedQuery<Role> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Account updateRoles(int accountID, Role role) {
        Account acc = em.find(Account.class, accountID);
        acc.AssignNewRole(role);
        return acc;
    }
}
