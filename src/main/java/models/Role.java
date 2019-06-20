package models;

import com.sun.mail.imap.protocol.ID;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDrole")
    private int IDrole;

    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Role_Permission",
            joinColumns = @JoinColumn(name = "IDrole"),
            inverseJoinColumns = @JoinColumn(name = "IDpermission"))
    private List<Permission> permissions;


    //region get/set
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return  permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public int getIDrole() {
        return IDrole;
    }

    public void setIDrole(int IDrole) {
        this.IDrole = IDrole;
    }
    //endregion

    public Role() {
    }

    public Role(int IDrole, String role, List<Permission> permissions) {
        this.IDrole = IDrole;
        this.role = role;
        this.permissions = permissions;
    }

    @Override
    public String toString(){
        return "ID: " + this.getIDrole() + " - Name: " + this.role;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Role) {
            return ((Role) o).getRole().equals(this.getRole());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getRole().hashCode() * 31;
    }
}
