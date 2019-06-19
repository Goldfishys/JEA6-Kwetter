package Services;

import DAL.Interfaces.IKweet;
import DAL.Interfaces.IUser;
import Websockets.KweetWebsocket;
import models.dtomodels.KweetDTO;
import models.Kweet;
import models.User;
import models.dtomodels.UserDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RequestScoped
public class KweetServices {

    @Inject
    private IKweet kweetRepo;

    @Inject
    private UserServices us;

    @Inject
    private KweetWebsocket kweetWebsocket;

    //region constructors
    public KweetServices() {
    }
    //endregion

    //region methods
    public List<KweetDTO> SearchKweets(String searchTerm, boolean mentions) {
        if (!searchTerm.equals("")) {
            return (mentions) ? kweetRepo.SearchMentions(searchTerm) : kweetRepo.SearchKweets(searchTerm);
        }
        return new ArrayList<>();
    }

    public KweetDTO PostKweet(Kweet kweet) {
        if(kweet != null && kweet.IsValid()){
            System.out.println("Posting the kweet");
            kweet = kweetRepo.PostKweet(kweet);
            KweetDTO kwt = this.GetKweet(kweet.getID());
            System.out.println("Kweet is valid");
            this.broadcastKweet(kwt);
            return kwt;
        }
        System.out.println("Kweet is invalid");
        return null;
    }

    public KweetDTO GetKweet(int kweetID) {
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

    public SortedSet<KweetDTO> GetRecentKweets(int accountID) {
        return kweetRepo.GetKweetsForAccount(accountID).stream().limit(10).collect(Collectors.toCollection(TreeSet::new));
    }

    public SortedSet<KweetDTO> GetTimeLIne(int accountID) {
        User user = us.GetUser(accountID);
        if(user != null) {
            List<UserDTO> following = us.GetFollowing(user.getId());
            SortedSet<KweetDTO> timeLine = GetRecentKweets(accountID);
            for (UserDTO followedUser : following) {
                timeLine.addAll(GetRecentKweets(followedUser.getId()));
            }
            return timeLine;
        }
        return new TreeSet<>();
    }

    public void broadcastKweet(KweetDTO kweet){
        List<UserDTO> followers = us.GetFollowers(kweet.getAuthorID());
        kweetWebsocket.broadcastPostedKweet(kweet, followers);
    }
    //endregion
}
