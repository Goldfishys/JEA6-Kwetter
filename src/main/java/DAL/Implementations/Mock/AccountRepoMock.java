package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IAccount;
import models.Account;
import models.User;

public class AccountRepoMock implements IAccount {
    private Database database;

    public AccountRepoMock(Database database) {
        this.database = database;
    }

    @Override
    public Account Register(String username) {
        Account acc = new Account(database.accounts.size());
        User usr = new User(username, acc);
        acc.setUser(usr);
        database.accounts.add(acc);

        Account acc2 = new Account(acc.getID());
        User usr2 = new User(username, acc2);
        acc2.setUser(usr2);
        return acc2;
    }
}
