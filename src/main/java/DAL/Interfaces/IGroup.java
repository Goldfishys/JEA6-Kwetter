package DAL.Interfaces;

import models.Account;
import models.Group;

public interface IGroup {
    void JoinGroup(Account account, Group group);
    void LeaveGroup(Account account, Group group);
    void CreateGroup(Group group);
    void RemoveGroup(Group group);
}