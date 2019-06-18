package Services;

import DAL.Interfaces.IKweet;
import DAL.Interfaces.IUser;
import Websockets.KweetWebsocket;
import models.Kweet;
import models.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RequestScoped
public class KweetServices {

    @Inject
    private IKweet kweetRepo;

    @Inject
    private IUser userRepo;

    @Inject
    private UserServices us;

    @Inject
    private KweetWebsocket kweetWebsocket;

    //region constructors
    public KweetServices() {
    }
    //endregion

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
        if(kweet != null && kweet.IsValid()){
            System.out.println("Posting the kweet");
            kweet = kweetRepo.PostKweet(kweet);
            System.out.println("Kweet is valid");
            this.broadcastKweet(kweet);
            return kweet;
        }
        System.out.println("Kweet is invalid");
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
        return kweetRepo.GetKweetsForAccount(accountID).stream().limit(10).collect(Collectors.toCollection(TreeSet::new));
    }

    public TreeSet<Kweet> GetTimeLIne(int accountID) {
        ArrayList<User> following = userRepo.GetFollowing(userRepo.GetUser(accountID).getFollowing());
        TreeSet<Kweet> timeLine = GetRecentKweets(accountID);
        for(User usr : following){
            timeLine.addAll(GetRecentKweets(usr.getAccount().getID()));
        }

        return timeLine;
    }

    public void broadcastKweet(Kweet kweet){
        List<User> followers = us.GetFollowers(kweet.getAuthor());
        kweetWebsocket.broadcastPostedKweet(kweet, followers);
    }
    //endregion
}
