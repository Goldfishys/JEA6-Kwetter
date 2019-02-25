package models;

import DAL.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

public class User {
    private Account account;
    private String username;
    private ArrayList<Kweet> kweets;
    private ArrayList<Account> followers;
    private ArrayList<Account> following;
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

    public ArrayList<Account> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<Account> followers) {
        this.followers = followers;
    }

    public ArrayList<Account> getFollowing() {
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
    //endregion

    //region Constructors
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

    //region KweetMethods
    public void PostKweet(String text) {
        if (text != null && text.length() <= 140) {
            Kweet kweet = new Kweet(text, this, new Date(), null, null);
            Database.getInstance().kweetRepo.PostKweet(account.getID(), kweet);
            kweets.add(kweet);
        }
    }

    public ArrayList<Kweet> GetRecentKweets() {
        ArrayList<Kweet> kweets = Database.getInstance().kweetRepo.GetKweetsForAccount(account.getID());
        return SortKweetsNewFirst(kweets).stream().limit(10).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Kweet> GetTimeLine(){
        ArrayList<Kweet> kweets = new ArrayList<>();
        kweets.addAll(this.GetRecentKweets());

        for(Account follow : following){
            kweets.addAll(follow.getUser().GetRecentKweets());
        }
        return SortKweetsNewFirst(kweets);
    }

    public ArrayList<Kweet> SortKweetsNewFirst(ArrayList<Kweet> kweets){
        kweets.sort(Comparator.comparing(o -> o.getDate(), Collections.reverseOrder()));
        return kweets;
    }
    //endregion

    //region Update username/profile methods
    public void UpdateUsername(String username) {
        if (username != "") {
            this.username = username;
            Database.getInstance().userRepo.UpdateUsername(account.getID(), username);
        }
    }

    public void LoadProfile() {
        profile = Database.getInstance().profileRepo.LoadProfileForAccount(account.getID());
    }

    public void UpdateProfile(String bio, String location, String websiteURL, String profilePicture) {
        if (bio != "" && location != "" && websiteURL != "" && profilePicture != "") {
            profile.UpdateProfile(bio, location, websiteURL, profilePicture);
            Database.getInstance().profileRepo.UpdateProfile(account.getID(), bio, location, websiteURL, profilePicture);
        }
    }
    //endregion

    //region Followers methods
    public void AddFollower(Account follower) {
        if (follower != null && !followers.contains(follower)) {
            followers.add(follower);
            follower.getUser().AddFollowing(this.getAccount());
        }
    }

    private void AddFollowing(Account toFollow) {
        if (!followers.contains(toFollow)) {
            this.following.add(toFollow);
        }
    }

    public void RemoveFollower(Account followerToRemove) {
        if (followerToRemove != null && followers.contains(followerToRemove)) {
            followers.remove(followerToRemove);
            followerToRemove.getUser().RemoveFollowing(this.getAccount());


        }
    }
    private void RemoveFollowing(Account followingToRemove) {
        if(following.contains(followingToRemove)){
            following.remove(followingToRemove);
        }
    }
    //endregion

    //endregion


}
