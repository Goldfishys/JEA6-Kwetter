package models;


import javax.persistence.Embeddable;

@Embeddable
public class Profile {
    private String profilePicture;
    private String bio;
    private String location;
    private String websiteURL;

    //region get/set
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }
    //endregion

    //region Constructors
    public Profile() {
        this.profilePicture = "";
        this.bio = "";
        this.location = "";
        this.websiteURL = "";
    }

    public Profile(String profilePicture, String bio, String location, String websiteURL) {
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.location = location;
        this.websiteURL = websiteURL;
    }
    //endregion


    //region methods
    public void UpdateProfile(Profile profile) {
        if(profile != null){
        this.bio = profile.bio;
        this.location = profile.location;
        this.websiteURL = profile.websiteURL;
        this.profilePicture = profile.profilePicture;
        }
    }

    public boolean IsValid() {
        if(profilePicture != null
                &&bio != null
                &&location != null
                &&websiteURL != null){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "bio: " + this.bio + " - profile picture: " + this.profilePicture + " - website url: " + this.websiteURL + " - location: " + this.location;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Profile) {
            if(obj.hashCode() == this.hashCode()) return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hashcode = 1;
        hashcode += this.getBio().hashCode() *33;
        hashcode += this.getWebsiteURL().hashCode() *33;
        hashcode += this.getLocation().hashCode() *33;
        hashcode += this.getProfilePicture().hashCode() *33;
        return hashcode;
    }
    //endregion
}
