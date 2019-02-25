package DAL.Interfaces;

import models.Account;
import models.Group;
import models.Permission;

import java.util.ArrayList;

public interface IGroup {
    void JoinGroup(Account account, Group group);
    void LeaveGroup(Account account, Group group);
    void CreateGroup(Group group);
    boolean HasPermission(int deleterAccountID, Permission permission);
    ArrayList<Permission> GetPermissionsForAccount(int accID);
}