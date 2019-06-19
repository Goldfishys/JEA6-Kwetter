package models.dtomodels;

public class JWTTokenDTO {
    private final String token;
    private final String username;
    private final int userid;

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public int getUserid() {
        return userid;
    }

    public JWTTokenDTO(String token, String username, int userid) {
        this.token = token;
        this.username = username;
        this.userid = userid;
    }
}
