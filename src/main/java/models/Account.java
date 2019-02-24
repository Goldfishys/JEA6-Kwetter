package models;

import DAL.Database;

public class Account {
    public int ID;
    public User user;

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
    public Account(int ID){
        this.ID = ID;
    }

    public Account(int ID, User user) {
        this.ID = ID;
        this.user = user;
    }
    //endregion

    //region methods
    public void JoinGroup(Group group) {
        group.AddAccount(this);
    }

    public void LeaveGroup(Group group) {
        group.RemoveAccount(this);
    }

    public void LoadUser() {
        user = Database.getInstance().userRepo.LoadUser(this.ID);
    }
    //endregion
}
