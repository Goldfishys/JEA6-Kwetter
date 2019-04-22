package Controllers;

import Services.AccountServices;
import models.Account;
import models.JwtToken;

import javax.inject.Inject;
import java.util.ArrayList;

public class AccountController {

    @Inject
    AccountServices as;

    public Account GetAccount(int accountID) {
        return as.GetAccount(accountID);
    }

    public ArrayList<Account> GetAccounts() {
        return as.GetAccounts();
    }

    public JwtToken login(String username, String password) {
        System.out.println("username: "+ username + " password: " + password);
        return as.login(username, password);
    }
}
