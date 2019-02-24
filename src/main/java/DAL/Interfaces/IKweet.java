package DAL.Interfaces;

import models.Kweet;

import java.util.ArrayList;

public interface IKweet {
    ArrayList<Kweet> SearchKweets(String searchTerm);
    ArrayList<Kweet> SearchMentions(String searchTerm);
    void PostKweet(int accountID, Kweet kweet);
    ArrayList<Kweet> GetKweetsForAccount(int accountID);
}
