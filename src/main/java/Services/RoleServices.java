package Services;

import DAL.Interfaces.IRole;
import models.Account;
import models.Role;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;

@RequestScoped
public class RoleServices {

    @Inject
    private IRole roleRepo;

    public RoleServices() {
    }

    public ArrayList<Role> getAllRoles(){
        return (ArrayList<Role>) roleRepo.getAllRoles();
    }

    public Account updateRoles(int accountID, Role role) {
        return roleRepo.updateRoles(accountID, role);
    }
}
