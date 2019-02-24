package models;

import DAL.Database;

import java.util.ArrayList;

public class Group {
    public String name;
    public ArrayList<Account> accounts;
    public Role role;

    public Group(String name, Role role){
        this.name = name;
        this.role = role;
        accounts = new ArrayList<>();
    }

    public Group(String name, ArrayList<Account> accounts, Role role){
        this.name = name;
        this.accounts = accounts;
        this.role = role;
    }

    public void AddAccount(Account account) {
        if(!accounts.contains(account)) {
            accounts.add(account);
            Database.getInstance().groupRepo.JoinGroup(account, this);
        }
    }

    public void RemoveAccount(Account account) {
        if(accounts.contains(account)) {
            accounts.remove(account);
            Database.getInstance().groupRepo.LeaveGroup(account, this);
        }
    }
}
