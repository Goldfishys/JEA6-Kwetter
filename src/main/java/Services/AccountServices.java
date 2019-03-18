package Services;

import DAL.Database;
import DAL.Implementations.Database.AccountRepo;
import models.Account;

import javax.inject.Inject;
import java.util.ArrayList;

public class AccountServices {

    @Inject
    private AccountRepo accountRepo;

    //region constructor
    public AccountServices() {
    }
    //end region

    //region methods
    public Account Register(String username, String password) {
        Account account =  new Account(username, password);
        Database.getInstance().accountRepo.Register(account);
        return account;
    }

    public Account GetAccount(int accountID) {
        return accountRepo.GetAccountByID(accountID);
    }

    public ArrayList<Account> GetAccounts() {
        return accountRepo.GetAccounts();
    }

    public Account login(String username, String password) {
        if(username != "" && password != "") {
            return accountRepo.login(username, password);
        }
        return null;
    }
    //endregion
}
