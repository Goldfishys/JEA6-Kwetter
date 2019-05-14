package Controllers;

import Services.RoleServices;
import models.Account;
import models.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class RoleController {

    @Inject
    private RoleServices rs;

    public ArrayList<Role> getAllRoles(){
        return rs.getAllRoles();
    }
    public Account updateRoles(int accountID, Role role){
        return rs.updateRoles(accountID, role);
    }
}
