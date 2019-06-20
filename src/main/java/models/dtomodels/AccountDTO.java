package models.dtomodels;

import models.Role;

import java.util.ArrayList;
import java.util.List;

public class AccountDTO {
    private int id;
    private String username;
    private List<Role> roles;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AccountDTO() {
        this.roles = new ArrayList<>();
    }

    public AccountDTO(int id, String username) {
        this.id = id;
        this.username = username;
        this.roles = new ArrayList<>();
    }

    @Override
    public String toString(){
        String str = "AccountDTO: ";
        for(Role role : roles){
            str+=role.toString();
        }
        return str;
    }
}
