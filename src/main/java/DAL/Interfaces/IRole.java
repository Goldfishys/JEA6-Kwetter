package DAL.Interfaces;

import models.Account;
import models.Role;
import models.dtomodels.AccountDTO;

import java.util.List;

public interface IRole {
    List<Role> getAllRoles();

    List<Role> updateRoles(int accountID, Role role);
}
