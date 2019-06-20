package ManagedBeans;

import Controllers.AccountController;
import Controllers.RoleController;
import models.Role;
import models.dtomodels.AccountDTO;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class UserBean implements Serializable {

    //region properties
    AccountController ac;
    RoleController rc;
    private ArrayList<Role> roles;
    private List<AccountDTO> accounts;
//    @ManagedProperty(value="#{param.role}")
    private String newRole;
//    @ManagedProperty(value="#{param.accountID}")
    private int accountID;
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

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
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
        for(AccountDTO acc : accounts){
            System.out.println("Account id: " + acc.getId());
            System.out.println("Roles size: "+acc.getRoles().size());
            for(Role r : acc.getRoles()){
                System.out.println(r.toString());
            }
        }
    }

    public void attrListener(ActionEvent event){
        this.accountID = (Integer) event.getComponent().getAttributes().get("accoundID");
        this.newRole = (String) event.getComponent().getAttributes().get("role");
    }

    public void EditRoles() {
        System.out.println("accid: " + this.accountID);
        System.out.println("Editing: " + this.newRole);
        for (Role role : roles) {
            System.out.println("Roles: " + role.getRole() + " - vs: " + this.newRole);
            if (role.getRole().equals(this.newRole)) {
                System.out.println("found role");
                List<Role> newRoles = rc.updateRoles(this.accountID, role);
                UpdateAccount(newRoles, this.accountID);
            }
        }
    }

    public void UpdateAccount(List<Role> newRoles, int id){
        for(AccountDTO acc : this.getAccounts()){
            if(acc.getId() == id){
                acc.setRoles(newRoles);
            }
        }
    }
    //endregion


}
