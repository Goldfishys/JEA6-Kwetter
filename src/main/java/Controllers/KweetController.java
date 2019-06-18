package Controllers;

import Services.KweetServices;
import models.DTOmodels.KweetDTO;
import models.Kweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Stateless
public class KweetController {

    @Inject
    private KweetServices ks;

    public List<KweetDTO> SearchKweets(String SearchTerm, boolean mentions){
        return ks.SearchKweets(SearchTerm, mentions);
    }

    public KweetDTO PostKweet(Kweet kweet) {
        return ks.PostKweet(kweet);
    }

    public KweetDTO GetKweet(int kweetID) {
        return ks.GetKweet(kweetID);
    }

    public Kweet UpdateKweet(Kweet kweet) {
        return ks.UpdateKweet(kweet);
    }

    public boolean DeleteKweet(int kweetID) {
        return ks.DeleteKweet(kweetID);
    }

    public SortedSet<KweetDTO> GetRecentKweets(int accountID){
        return ks.GetRecentKweets(accountID);
    }

    public SortedSet<KweetDTO> GetTimeLine(int accountID){
        return ks.GetTimeLIne(accountID);
    }
}
