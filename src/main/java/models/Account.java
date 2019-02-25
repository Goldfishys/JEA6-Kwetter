package models;

import DAL.Database;

import java.util.ArrayList;

public class Account {
    private int ID;
    private User user;
    private ArrayList<Permission> permissions;

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
    public ArrayList<Permission> getPermissions() {
        return permissions;
    }
    //endregion

    //region constructors
    public Account(int ID){
        this.ID = ID;
        permissions = new ArrayList<>();
    }

    public Account(int ID, User user) {
        this.ID = ID;
        this.user = user;
        permissions = new ArrayList<>();
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

    public void LoadGroups(){
        permissions = Database.getInstance().groupRepo.GetPermissionsForAccount(this.ID);
    }
    //endregion
}
