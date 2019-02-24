package models;

public class Profile {
    private String profilePicture;
    private String bio;
    private String location;
    private String websiteURL;

    //region get/set
    public String getProfilePicture() {
        return profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getWebsiteURL() {
        return websiteURL;
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

    public void UpdateProfile(String bio, String location, String websiteURL, String profilePicture) {
        this.bio = bio;
        this.location = location;
        this.websiteURL = websiteURL;
        this.profilePicture = profilePicture;
    }

}
