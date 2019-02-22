package DAL.Implementations.Mock;

import DAL.Interfaces.IAccount;
import models.Account;
import models.Group;

import java.util.ArrayList;

public class AccountRepoMock implements IAccount {
    public ArrayList<Group> groups;

    public AccountRepoMock() {
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
