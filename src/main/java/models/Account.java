package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "IDaccount")
    private int ID;

    @Column(name = "Password")
    private String password;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="Account_Role",
            joinColumns=@JoinColumn(name="IDaccount"),
            inverseJoinColumns = @JoinColumn(name = "IDrole"))
    private List<Role> roles;

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

    public List<Role> getRoles() {
        return  roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
        roles = new ArrayList<>();
    }

    public Account(String name, String password){
        this.user = new User(name, this);
        this.password = password;
        roles = new ArrayList<>();
    }

    public Account(int ID){
        this.ID = ID;
        roles = new ArrayList<>();
    }
    //endregion

    //region methods
    @Override
    public String toString(){
        return "ID: " + this.getID() + "User: " + this.getUser();
    }

    public void AssignNewRole(Role role) {
        if(role != null){
            System.out.println("Assigned new role succesfully");
            this.roles.clear();
            this.roles.add(role);
        }else{
            System.out.println("Couldn't find the role");
        }

    }
    //endregion
}
