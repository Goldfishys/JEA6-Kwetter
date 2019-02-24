package models;

import DAL.Database;

import java.util.ArrayList;

public class KwetterService {


    //region Constructors
    public KwetterService() {

    }
    //endregion

    //region methods
    public ArrayList<Kweet> SearchKweets(String searchTerm, boolean mentions) {
        if (searchTerm != "") {
            //do database logic
            ArrayList<Kweet> foundKweets = (mentions) ? Database.getInstance().kweetRepo.SearchMentions(searchTerm) : Database.getInstance().kweetRepo.SearchKweets(searchTerm);
            return foundKweets;
        }
        return new ArrayList<>();
    }

    public Account Register(String username) {
        return Database.getInstance().accountRepo.Register(username);
    }
    //endregion
}
