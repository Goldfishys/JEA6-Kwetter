package DAL.Interfaces;

import models.Account;
import models.Role;

import java.util.List;

public interface IRole {
    List<Role> getAllRoles();

    Account updateRoles(int accountID, Role role);
}
