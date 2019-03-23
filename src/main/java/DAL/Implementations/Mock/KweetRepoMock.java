package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IKweet;
import models.Account;
import models.Kweet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

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
    public Kweet PostKweet(Kweet kweet) {
        for (Account account : database.accounts) {
            if (account.getID() == kweet.getAuthor()) {
                account.getUser().getKweets().add(kweet);
                return kweet;
            }
        }
        return null;
    }

    @Override
    public Kweet UpdateKweet(Kweet kweet) {
        return null;
    }

    @Override
    public TreeSet<Kweet> GetKweetsForAccount(int id) {
        TreeSet<Kweet> results = new TreeSet<>();
        for (Account acc : database.accounts) {
            for (Kweet kweet : acc.getUser().getKweets()) {
                if (kweet.getAuthor() == id) results.add(kweet);
            }
        }
        return results;
    }

    @Override
    public Kweet GetKweet(int KweetID) {
        return null;
    }

    @Override
    public boolean DeleteKweet(int kweetid) {
        for (Iterator<Account> accIT = database.accounts.iterator(); accIT.hasNext(); ) {
            Account acc = accIT.next();
            for (Iterator<Kweet> kweetIT = acc.getUser().getKweets().iterator(); kweetIT.hasNext(); ) {
                Kweet kwt = kweetIT.next();
                if(kwt.getID() == kweetid) {
                    kweetIT.remove();
                    return true;
                }
            }
        }
        return false;
    }
}
