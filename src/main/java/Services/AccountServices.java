package Services;

import DAL.Database;
import DAL.Interfaces.IAccount;
import models.Account;
import models.Role;
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

    @Inject
    private UserServices us;

    @Inject
    private RoleServices rs;

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
        List<AccountDTO> accs = accountRepo.GetAccounts();
        for(AccountDTO acc : accs){
            List<Role> roles = rs.getRolesForUser(acc.getId());
            roles.forEach(p -> System.out.println("@Role" + p.toString()));
            acc.setRoles(roles);
        }
        accs.forEach(p -> accs.toString());
        return accs;
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
