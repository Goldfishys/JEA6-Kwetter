package DAL.Interfaces;

import models.User;

public interface IUser {

    User LoadUser(int ID);
    void UpdateUsername(int id, String username);
}
