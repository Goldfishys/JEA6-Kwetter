package DAL.Interfaces;

import models.Account;
import models.dtomodels.AccountDTO;

import java.util.List;

public interface IAccount {
    Account Register(Account account);
    Account GetAccountByID(int ID);
    List<AccountDTO> GetAccounts();
    Account login(String username, String password);
}
