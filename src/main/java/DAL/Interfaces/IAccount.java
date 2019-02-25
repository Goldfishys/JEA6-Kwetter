package DAL.Interfaces;

import models.Account;

public interface IAccount {
    Account Register(String username);
    Account GetAccountByID(int ID);
}
