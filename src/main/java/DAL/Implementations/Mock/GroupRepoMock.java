package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IGroup;
import models.Account;
import models.Group;

import java.util.ArrayList;

public class GroupRepoMock implements IGroup {
    public ArrayList<Group> groups;

    public GroupRepoMock(Database database) {
        groups = new ArrayList<>();
    }

    @Override
    public void JoinGroup(Account account, Group group) {
        if(!group.accounts.contains(account)){
            group.AddAccount(account);
        }
    }

    @Override
    public void LeaveGroup(Account account, Group group) {
        int index = groups.indexOf(group);
        if(index != -1){
            groups.get(index).RemoveAccount(account);
        }
    }

    @Override
    public void CreateGroup(Group group){
        if(!groups.contains(group)){
            groups.add(group);
        }
    }

    @Override
    public void RemoveGroup(Group group) {

    }
}
