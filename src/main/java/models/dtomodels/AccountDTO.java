package models.dtomodels;

import models.Role;

public class AccountDTO {
    private int id;
    private String username;
    private Role role;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRoles() {
        return role;
    }

    public void setRoles(Role role) {
        this.role = role;
    }

    public AccountDTO() {
    }

    public AccountDTO(int id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
