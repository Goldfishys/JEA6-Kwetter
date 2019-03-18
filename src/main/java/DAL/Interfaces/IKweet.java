package DAL.Interfaces;

import models.Kweet;

import java.util.ArrayList;
import java.util.TreeSet;

public interface IKweet {
    ArrayList<Kweet> SearchKweets(String searchTerm);
    ArrayList<Kweet> SearchMentions(String searchTerm);

    TreeSet<Kweet> GetKweetsForAccount(int accountID);

    Kweet GetKweet(int KweetID);
    Kweet PostKweet(Kweet kweet);
    Kweet UpdateKweet(Kweet kweet);
    boolean DeleteKweet(int kweetID);
}
