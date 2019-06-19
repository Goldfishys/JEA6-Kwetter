package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDuser")
    private int id;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_IDaccount")
    private Account account;

    @Column(name = "Username")
    private String username;

    @Transient
    private List<Kweet> kweets;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Followers",
            joinColumns = @JoinColumn(name = "IDAccountToFollow"),
            inverseJoinColumns = @JoinColumn(name = "IDuserFollower"))
    private List<Account> followers;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Followers",
            joinColumns = @JoinColumn(name = "IDuserFollower"),
            inverseJoinColumns = @JoinColumn(name = "IDAccountToFollow"))
    private List<Account> following;

    @Embedded
    private Profile profile;

    //region get/set
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(ArrayList<Kweet> kweets) {
        this.kweets = kweets;
    }

    public List<Account> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<Account> followers) {
        this.followers = followers;
    }

    public List<Account> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<Account> following) {
        this.following = following;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //endregion

    //region Constructors
    public User() {
        kweets = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
    }

    public User(int id, Account account, String username) {
        this.id = id;
        this.account = account;
        this.username = username;
        kweets = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
        profile = new Profile();
    }

    public User(String username, Account account) {
        this.account = account;
        this.username = username;
        kweets = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
        profile = new Profile();
    }
    //endregion

    //region Methods
    public void AddFollower(Account follower) {
        if(!followers.contains(follower)) followers.add(follower);
    }

    public void RemoveFollower(Account followerToRemove) {
        this.getFollowers().removeIf(acc -> acc.getID() == followerToRemove.getID());
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " - Username: " + this.username + " - " + this.profile.toString() + " followers: " + followers.size() + " following: " + following.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return ((User) obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int code = 0;
        code += this.getId() * 33;
        return code;
    }

    //endregion


}
