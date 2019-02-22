package models;

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
        accounts.add(account);
    }
}
