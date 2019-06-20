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
    public List<Role> updateRoles(int accountID, Role role) {
        Account acc = em.find(Account.class, accountID);
        acc.AssignNewRole(role);
        em.merge(acc);
        return acc.getRoles();
    }

    @Override
    public List<Role> getRolesForUser(int accountID) {
        String sql = "SELECT r.* FROM Role r join Account_Role ar on r.IDrole=ar.IDrole where ar.IDaccount=:accountID";
        List<Role> roles = em.createNativeQuery(sql, Role.class)
                .setParameter("accountID", accountID)
                .getResultList();
        for(Object r : roles){
            System.out.println(r == null);
            System.out.println("RoleFoundIs:" + r.toString());
        }
        return roles;
    }
}
