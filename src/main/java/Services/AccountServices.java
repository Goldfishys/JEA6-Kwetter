package Services;

import DAL.Database;
import DAL.Interfaces.IAccount;
import models.Account;
import models.JwtToken;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class AccountServices {

    @Inject
    private IAccount accountRepo;

    //region constructor
    public AccountServices() {
    }
    //end region

    //region methods
    public Account Register(String username, String password) {
        Account account = new Account(username, password);
        Database.getInstance().accountRepo.Register(account);
        return account;
    }

    public Account GetAccount(int accountID) {
        return accountRepo.GetAccountByID(accountID);
    }

    public List<Account> GetAccounts() {
        return accountRepo.GetAccounts();
    }

    public JwtToken login(String username, String password) {
        if (username != null && password != null && username != "" && password != "") {
            Account acc = accountRepo.login(username, password);
            if (acc != null) {
                return new JwtToken(acc, username);
            }
            return null;
        }
        return null;
    }
    //endregion
}
