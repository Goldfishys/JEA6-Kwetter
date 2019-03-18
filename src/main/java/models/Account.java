package models;

import DAL.Database;

import javax.persistence.*;
import java.util.ArrayList;


@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "IDaccount")
    private int ID;

    @Column(name = "Password")
    private String password;

    @Transient
    private User user;

    @Transient
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //endregion

    //region constructors
    public Account() {
    }

    public Account(String name, String password){
        this.user = new User(name, this);
        this.password = password;
        permissions = new ArrayList<>();
    }

    public Account(int ID){
        this.ID = ID;
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
        user = Database.getInstance().userRepo.GetUser(this.ID);
    }

    public void LoadGroups(){
        permissions = Database.getInstance().groupRepo.GetPermissionsForAccount(this.ID);
    }

    @Override
    public String toString(){
        return "ID: " + this.getID() + "User: " + this.getUser();
    }
    //endregion
}
