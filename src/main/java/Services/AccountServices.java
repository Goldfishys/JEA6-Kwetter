package Services;

import DAL.Database;
import DAL.Implementations.Database.AccountRepo;
import models.Account;
import models.JwtToken;

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
        Account account = new Account(username, password);
        Database.getInstance().accountRepo.Register(account);
        return account;
    }

    public Account GetAccount(int accountID) {
        return accountRepo.GetAccountByID(accountID);
    }

    public ArrayList<Account> GetAccounts() {
        return accountRepo.GetAccounts();
    }

    public JwtToken login(String username, String password) {
        if (username != null && password != null && username != "" && password != "") {
            Account acc = accountRepo.login(username, password);
            if(acc != null){
                System.out.println("found the account, now creating token");
                JwtToken token = new JwtToken(acc, username);
                if(token.complete()) return token;
            }
            System.out.println("couldnt find username/password combi");
        }
        return null;
    }
    //endregion
}
