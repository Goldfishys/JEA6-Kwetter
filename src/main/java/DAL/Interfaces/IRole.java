package DAL.Interfaces;

import models.Role;

import java.util.List;

public interface IRole {
    List<Role> getAllRoles();
    List<Role> updateRoles(int accountID, Role role);

    List getRolesForUser(int accountID);
}
