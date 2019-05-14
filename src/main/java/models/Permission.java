package models;


import javax.persistence.*;

@Entity
@Table(name = "Permission")
public class Permission {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "IDpermission")
    private int IDpermission;
    private String permission;

    //region get/set
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getIDpermission() {
        return IDpermission;
    }

    public void setIDpermission(int IDpermission) {
        this.IDpermission = IDpermission;
    }

    //endregion

    public Permission() {
    }
}
