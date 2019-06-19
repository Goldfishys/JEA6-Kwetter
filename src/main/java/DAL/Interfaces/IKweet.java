package DAL.Interfaces;

import models.dtomodels.KweetDTO;
import models.Kweet;

import java.util.List;
import java.util.SortedSet;

public interface IKweet {
    List<KweetDTO> SearchKweets(String searchTerm);
    List<KweetDTO> SearchMentions(String searchTerm);

    SortedSet<KweetDTO> GetKweetsForAccount(int accountID);

    KweetDTO GetKweet(int KweetID);
    Kweet PostKweet(Kweet kweet);
    Kweet UpdateKweet(Kweet kweet);
    boolean DeleteKweet(int kweetID);
}
