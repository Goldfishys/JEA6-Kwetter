package Controllers;

import Services.RoleServices;
import models.Account;
import models.Role;
import models.dtomodels.AccountDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RoleController {

    @Inject
    private RoleServices rs;

    public ArrayList<Role> getAllRoles(){
        return rs.getAllRoles();
    }
    public List<Role> updateRoles(int accountID, Role role){
        return rs.updateRoles(accountID, role);
    }
}
