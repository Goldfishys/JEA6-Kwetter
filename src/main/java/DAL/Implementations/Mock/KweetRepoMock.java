package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IKweet;
import models.Account;
import models.Kweet;

import java.util.ArrayList;

public class KweetRepoMock implements IKweet {
    public Database database;

    public KweetRepoMock(Database database) {
        this.database = database;
    }

    @Override
    public ArrayList<Kweet> SearchKweets(String searchTerm) {
        String searchTermLower = searchTerm.toLowerCase();
        ArrayList<Kweet> results = new ArrayList<>();
        for (Account acc : database.accounts) {
            for (Kweet kweet : acc.getUser().getKweets()) {
                if (kweet.getText().toLowerCase().contains(searchTermLower)) results.add(kweet);
            }
        }
        return results;
    }

    @Override
    public ArrayList<Kweet> SearchMentions(String searchTerm) {
        ArrayList<Kweet> results = new ArrayList<>();

        for (Account acc : database.accounts) {
            for (Kweet kweet : acc.getUser().getKweets()) {
                if (kweet.HasMention(searchTerm)) results.add(kweet);
            }
        }
        return results;
    }

    @Override
    public void PostKweet(int accountID, Kweet kweet) {
        for (Account account : database.accounts) {
            if (account.getID() == accountID) {
                account.getUser().getKweets().add(kweet);
            }
        }
    }

    @Override
    public ArrayList<Kweet> GetKweetsForAccount(int id) {
        ArrayList<Kweet> results = new ArrayList<>();

        for (Account acc : database.accounts) {
            for (Kweet kweet : acc.getUser().getKweets()) {
                if (kweet.getAuthor().getAccount().getID() == id) results.add(kweet);
            }
        }
        return results;
    }


}
