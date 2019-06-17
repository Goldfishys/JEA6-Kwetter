package DAL.Interfaces;

import models.Account;

import java.util.List;

public interface IAccount {
    Account Register(Account account);
    Account GetAccountByID(int ID);
    List<Account> GetAccounts();
    Account login(String username, String password);
}
