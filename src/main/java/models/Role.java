package models;

import java.util.List;

public class Role {
    public String name;
    public List<Permission> permission;

    public Role() {
    }

    public Role(String name, List<Permission> permission){
        this.name = name;
        this.permission = permission;
    }

}
