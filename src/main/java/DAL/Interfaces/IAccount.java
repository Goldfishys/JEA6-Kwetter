package DAL.Interfaces;

import models.Account;

public interface IAccount {
    void Register(Account account);
    Account GetAccountByID(int ID);
}
