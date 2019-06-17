package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IAccount;
import models.Account;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class AccountRepoMock implements IAccount {
    private Database database;

    public AccountRepoMock(Database database) {
        this.database = database;
    }

    @Override
    public Account Register(Account account) {
        int id = database.accounts.size() +1;
        account.setID(id);
        database.accounts.add(account);
        return account;
    }

    public Account GetAccountByID(int ID){
        for(Account acc : database.accounts){
            if(acc.getID() == ID){
                return acc;
            }
        }
        return null;
    }

    @Override
    public List<Account> GetAccounts() {
        throw new NotImplementedException();
    }

    @Override
    public Account login(String username, String password) {
        return null;
    }
}
