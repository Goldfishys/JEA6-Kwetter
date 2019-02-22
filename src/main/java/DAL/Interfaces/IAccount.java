package DAL.Interfaces;

import models.Account;
import models.Group;

public interface IAccount {

    void JoinGroup(Account account, Group group);
    void LeaveGroup(Account account, Group group);
    void CreateGroup(Group group);
    void RemoveGroup(Group group);
}
