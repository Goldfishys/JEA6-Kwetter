package Services;

import DAL.Database;
import DAL.Interfaces.IAccount;
import models.Account;
import models.dtomodels.AccountDTO;
import models.dtomodels.JWTTokenDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class AccountServices {

    @Inject
    private IAccount accountRepo;

    @Inject
    JWTService jwtService;

    //region constructor
    public AccountServices() {
    }
    //end region

    //region methods
    public Account Register(Account acc) {
        return accountRepo.Register(acc);
    }

    public Account GetAccount(int accountID) {
        return accountRepo.GetAccountByID(accountID);
    }

    public List<AccountDTO> GetAccounts() {
        return accountRepo.GetAccounts();
    }

    public JWTTokenDTO login(String username, String password) {
        if (username != null && password != null && username != "" && password != "") {
            Account acc = accountRepo.login(username, password);
            if (acc != null) {
//                String token = jwtService.createToken(username, acc.getID());
                return new JWTTokenDTO("SecretToken",username, acc.getID());
            }
            return null;
        }
        return null;
    }
    //endregion
}
