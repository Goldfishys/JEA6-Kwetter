package models.dtomodels;

public class UserDTO {

    //region properties
    private int id;
    private String username;
    //endregion


    //region get/set
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    //endregion


    //region constructors
    public UserDTO() {
    }

    public UserDTO(int id, String username) {
        this.id = id;
        this.username = username;
    }
    //endregion
}
