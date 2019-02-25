package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IGroup;
import models.Account;
import models.Group;
import models.Permission;

import java.util.ArrayList;

public class GroupRepoMock implements IGroup {
    public ArrayList<Group> groups;

    public GroupRepoMock(Database database) {
        groups = new ArrayList<>();
    }

    @Override
    public void JoinGroup(Account account, Group group) {
        if (!group.accounts.contains(account)) {
            group.AddAccount(account);
        }
    }

    @Override
    public void LeaveGroup(Account account, Group group) {
        int index = groups.indexOf(group);
        if (index != -1) {
            groups.get(index).RemoveAccount(account);
        }
    }

    @Override
    public void CreateGroup(Group group) {
        if (!groups.contains(group)) {
            groups.add(group);
        }
    }

    @Override
    public boolean HasPermission(int deleterAccountID, Permission permission) {
        for (Group group : groups) {
            if (group.role.permission.contains(permission)) {
                for (Account acc : group.accounts) {
                    if (acc.getID() == deleterAccountID){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<Permission> GetPermissionsForAccount(int accID) {
        ArrayList<Permission> permissions = new ArrayList<>();
        Account acc = Database.getInstance().accountRepo.GetAccountByID(accID);
        for (Group group : groups) {
            if (group.accounts.contains(acc)) {
                for(Permission perm : group.role.permission){
                    if(!permissions.contains(perm)){
                        permissions.add(perm);
                    }
                }
            }
        }
        return permissions;
    }


}
