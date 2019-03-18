package Controllers;

import Services.KweetServices;
import models.Kweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.TreeSet;

@Stateless
public class KweetController {

    @Inject
    private KweetServices ks;

    public ArrayList<Kweet> SearchKweets(String SearchTerm, boolean mentions){
        return ks.SearchKweets(SearchTerm, mentions);
    }

    public Kweet PostKweet(Kweet kweet) {
        return ks.PostKweet(kweet);
    }

    public Kweet GetKweet(int kweetID) {
        return ks.GetKweet(kweetID);
    }

    public Kweet UpdateKweet(Kweet kweet) {
        return ks.UpdateKweet(kweet);
    }

    public boolean DeleteKweet(int kweetID) {
        return ks.DeleteKweet(kweetID);
    }

    public TreeSet<Kweet> GetRecentKweets(int accountID){
        return ks.GetRecentKweets(accountID);
    }
}