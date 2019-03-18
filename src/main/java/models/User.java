package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "IDuser")
    private int id;

    @OneToOne
    @JoinColumn(name = "User_IDaccount")
    private Account account;

    @Column(name = "Username")
    private String username;

    @Transient
    private ArrayList<Kweet> kweets;

    @OneToMany
    @JoinTable(name="Followers",
            joinColumns=@JoinColumn(name="IDAccountToFollow"),
            inverseJoinColumns=@JoinColumn(name="IDuserFollower"))
    private List<Account> followers;

    @OneToMany
    @JoinTable(name="Followers",
            joinColumns=@JoinColumn(name="IDuserFollower"),
            inverseJoinColumns=@JoinColumn(name="IDAccountToFollow"))
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

    public ArrayList<Kweet> getKweets() {
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

//    public ArrayList<Kweet> GetTimeLine(){
//        ArrayList<Kweet> kweets = new ArrayList<>();
//        kweets.addAll(this.GetRecentKweets());
//
//        for(Account follow : following){
//            kweets.addAll(follow.getUser().GetRecentKweets());
//        }
//        return SortKweetsNewFirst(kweets);
//    }

    public void AddFollower(Account follower) {
            followers.add(follower);
    }

//    private void AddFollowing(Account toFollow) {
//        if (!followers.contains(toFollow)) {
//            this.following.add(toFollow);
//        }
//    }

    public void RemoveFollower(Account followerToRemove) {
        followers.remove(followerToRemove);
    }

//    private void RemoveFollowing(Account followingToRemove) {
//        if(following.contains(followingToRemove)){
//            following.remove(followingToRemove);
//        }
//    }

    @Override
    public String toString(){
        return "ID: " + this.id + " - Username: " + this.username +" - " + this.profile.toString() + " followers: " + followers.size() + " following: "+ following.size();
    }

    //endregion


}
