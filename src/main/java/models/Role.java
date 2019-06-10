package models;

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

    @ManyToMany(fetch = FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o.getClass() == Role.class)) {
            return false;
        }

        Role r = (Role) o;
        if(r.getRole() == this.getRole()){
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.getRole().hashCode() * 31;
    }
}
