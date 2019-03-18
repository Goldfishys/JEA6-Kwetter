package Services;

import DAL.Implementations.Database.KweetRepo;
import models.Kweet;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Stateless
public class KweetServices {

    private KweetRepo kweetRepo;

    //region constructors
    public KweetServices() {
        kweetRepo = new KweetRepo();
    }
    //end region

    //region methods
    public ArrayList<Kweet> SearchKweets(String searchTerm, boolean mentions) {
        if (searchTerm != "") {
            //do database logic
            ArrayList<Kweet> foundKweets = (mentions) ? kweetRepo.SearchMentions(searchTerm) : kweetRepo.SearchKweets(searchTerm);
            return foundKweets;
        }
        return new ArrayList<>();
    }

    public Kweet PostKweet(Kweet kweet) {
        if(kweet.IsValid()){
            return kweetRepo.PostKweet(kweet);
        }
        return null;
    }

    public Kweet GetKweet(int kweetID) {
        return kweetRepo.GetKweet(kweetID);
    }

    public Kweet UpdateKweet(Kweet kweet) {
        if(kweet.IsValid()){
            return kweetRepo.UpdateKweet(kweet);
        }
        return null;
    }

    public boolean DeleteKweet(int kweetID) {
        return kweetRepo.DeleteKweet(kweetID);
    }

    public TreeSet<Kweet> GetRecentKweets(int accountID) {
        TreeSet<Kweet> kweets = kweetRepo.GetKweetsForAccount(accountID);
        return kweets.stream().limit(10).collect(Collectors.toCollection(TreeSet::new));
    }
    //end region
}
