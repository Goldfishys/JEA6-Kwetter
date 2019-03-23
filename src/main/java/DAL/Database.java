package DAL;

import DAL.Implementations.Database.AccountRepo;
import DAL.Implementations.Database.KweetRepo;
import DAL.Implementations.Mock.*;
import DAL.Interfaces.*;
import models.Account;

import java.util.ArrayList;

public class Database {
    private static Database instance;
    public IAccount accountRepo;
    public IGroup groupRepo;
    public IKweet kweetRepo;
    public IProfile profileRepo;
    public IUser userRepo;
    public ArrayList<Account> accounts;

    private Database(){
        accountRepo = new AccountRepo();
        groupRepo = new GroupRepoMock(this);
        kweetRepo = new KweetRepo();
        profileRepo = new ProfileRepoMock(this);
        userRepo = new UserRepoMock(this);
        accounts = new ArrayList<>();
    }

    static {
        instance = new Database();
    }

    public static Database getInstance() {
        return instance;
    }
}
