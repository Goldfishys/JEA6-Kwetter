package DAL.Implementations.Mock;

import DAL.Database;
import DAL.Interfaces.IKweet;
import models.Account;
import models.dtomodels.KweetDTO;
import models.Kweet;

import java.util.*;

public class KweetRepoMock implements IKweet {
    public Database database;

    public KweetRepoMock(Database database) {
        this.database = database;
    }

    @Override
    public List<KweetDTO> SearchKweets(String searchTerm) {
        String searchTermLower = searchTerm.toLowerCase();
        List<KweetDTO> results = new ArrayList<>();
        for (Account acc : database.accounts) {
            for (Kweet kweet : acc.getUser().getKweets()) {
                if (kweet.getText().toLowerCase().contains(searchTermLower)) results.add(new KweetDTO(kweet, acc.getUser().getUsername()));
            }
        }
        return results;
    }

    @Override
    public List<KweetDTO> SearchMentions(String searchTerm) {
        List<KweetDTO> results = new ArrayList<>();

        for (Account acc : database.accounts) {
            for (Kweet kweet : acc.getUser().getKweets()) {
                if (kweet.HasMention(searchTerm)) results.add(new KweetDTO(kweet, acc.getUser().getUsername()));
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
    public SortedSet<KweetDTO> GetKweetsForAccount(int id) {
        SortedSet<KweetDTO> results = new TreeSet<>();
        for (Account acc : database.accounts) {
            for (Kweet kweet : acc.getUser().getKweets()) {
                if (kweet.getAuthor() == id) results.add(new KweetDTO(kweet, acc.getUser().getUsername()));
            }
        }
        return results;
    }

    @Override
    public KweetDTO GetKweet(int kweetID) {
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
