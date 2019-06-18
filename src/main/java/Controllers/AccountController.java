package Controllers;

import Services.AccountServices;
import models.Account;
import models.DTOmodels.JWTTokenDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AccountController {

    @Inject
    AccountServices as;

    public Account GetAccount(int accountID) {
        return as.GetAccount(accountID);
    }

    public List<Account> GetAccounts() {
        return as.GetAccounts();
    }

    public JWTTokenDTO login(String username, String password) {
        return as.login(username, password);
    }
}
