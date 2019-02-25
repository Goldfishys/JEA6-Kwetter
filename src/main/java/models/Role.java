package models;

import java.util.ArrayList;

public class Role {
    public String name;
    public ArrayList<Permission> permission;

    public Role(String name, ArrayList<Permission> permission){
        this.name = name;
        this.permission = permission;
    }

    public Role() {

    }
}
