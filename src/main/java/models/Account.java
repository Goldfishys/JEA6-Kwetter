package models;

import DAL.Implementations.Mock.AccountRepoMock;
import DAL.Interfaces.IAccount;

import java.util.ArrayList;

public class Account {
    public int ID;
    public User user;
    public ArrayList<Group> groups;
    IAccount accountRepo;

    //region get/set
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //endregion


    //region constructors
    public Account(){
        this.groups = new ArrayList<>();
        accountRepo = new AccountRepoMock();
    }

    public Account(int ID, User user, ArrayList<Group> groups) {
        this.ID = ID;
        this.user = user;
        this.groups = groups;
        accountRepo = new AccountRepoMock();
    }
    //endregion

    //region methods
    public void JoinGroup(Group group){
        //check if account is already in that group before adding
        if(!groups.contains(group)){
            group.AddAccount(this);
            groups.add(group);
            accountRepo.JoinGroup(this, group);
        }
    }

    public void LeaveGroup(){

    }
    //endregion
}
