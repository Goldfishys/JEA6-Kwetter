package ManagedBeans;

import Controllers.AccountController;
import Controllers.RoleController;
import models.Account;
import models.Role;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@ViewScoped
public class UserBean implements Serializable {

    //region properties
    AccountController ac;
    RoleController rc;
    private ArrayList<Role> roles;
    private ArrayList<Account> accounts;
    private String newRole;
    //endregion


    //region get/set
    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public String getNewRole() {
        return newRole;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    //endregion


    //region consturctors
    @Inject
    public UserBean(AccountController ac, RoleController rc) {
        this.ac = ac;
        this.rc = rc;
        accounts = new ArrayList<>();
        RetrieveAccounts();
        roles = rc.getAllRoles();
        for(Role role : roles){
            System.out.println("Role: " + role.getRole());
        }
    }
    //endregion


    //region methods
    public void RetrieveAccounts(){
        System.out.println("getting accounts");
        this.setAccounts(ac.GetAccounts());
        for(Account acc : accounts){
            System.out.println("Account id: " + acc.getID());
        }
    }

    public void EditRoles(int accountID) {
        System.out.println("Editing: " + newRole);
        for (Role role : roles) {
            System.out.println("Roles: " + role.getRole() + " - vs: " + newRole);
            if (role.getRole().equals(newRole)) {
                System.out.println("found role");
                Account acc = rc.updateRoles(accountID, role);
                UpdateAccount(acc);
            }
        }
    }

    public void UpdateAccount(Account account){
        for(Account acc : this.getAccounts()){
            if(acc.getID() == account.getID()){
                accounts.set(accounts.indexOf(acc), account);
            }
        }
    }
    //endregion


}
