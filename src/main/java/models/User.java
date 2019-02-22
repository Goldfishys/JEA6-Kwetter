package models;

import java.util.List;

public class User {
    public String username;
    public List<Kweet> kweets;
    public List<Account> followers;
    public List<Account> following;
    public Profile profile;
}
