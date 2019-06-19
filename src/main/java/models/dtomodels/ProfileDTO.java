package models.dtomodels;

public class ProfileDTO {

    //region properties
    private String profilePicture;
    private String bio;
    private String location;
    private String websiteURL;
    private String username;
    //endregion


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

    public String getUsername() {
        return username;
    }
    //enderegion


    //region constructors
    public ProfileDTO() {
    }

    public ProfileDTO(String profilePicture, String bio, String location, String websiteURL, String username) {
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.location = location;
        this.websiteURL = websiteURL;
        this.username = username;
    }
    //endregion
}
