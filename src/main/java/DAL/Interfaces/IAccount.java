package DAL.Interfaces;

import models.Account;

import java.util.ArrayList;

public interface IAccount {
    Account Register(Account account);
    Account GetAccountByID(int ID);
    ArrayList<Account> GetAccounts();
    Account login(String username, String password);
}
