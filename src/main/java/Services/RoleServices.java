package Services;

import DAL.Interfaces.IRole;
import models.Account;
import models.Role;
import models.dtomodels.AccountDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class RoleServices {

    @Inject
    private IRole roleRepo;

    public RoleServices() {
    }

    public ArrayList<Role> getAllRoles(){
        return (ArrayList<Role>) roleRepo.getAllRoles();
    }

    public List<Role> updateRoles(int accountID, Role role) {
        return roleRepo.updateRoles(accountID, role);
    }

    public List<Role> getRolesForUser(int accountID) {
        return roleRepo.getRolesForUser(accountID);
    }
}
