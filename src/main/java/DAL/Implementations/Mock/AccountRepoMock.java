package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IAccount;
import models.Account;

public class AccountRepoMock implements IAccount {
    private Database database;

    public AccountRepoMock(Database database) {
        this.database = database;
    }

    @Override
    public void Register(Account account) {
        int id = database.accounts.size() +1;
        account.setID(id);
        database.accounts.add(account);
    }

    public Account GetAccountByID(int ID){
        for(Account acc : database.accounts){
            if(acc.getID() == ID){
                return acc;
            }
        }
        return null;
    }
}
