package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Kweet {
    private int ID;
    private String text;
    private User author;
    private List<Activity> activity;
    private ArrayList<User> mentions;
    private Date date;

    //region get/set
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public ArrayList<User> getMentions() {
        return mentions;
    }

    public void setMentions(ArrayList<User> mentions) {
        this.mentions = mentions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    //endregion

    //region Constructors
    public Kweet(int ID, String text, User author, Date date, List<Activity> activity, ArrayList<User> mentions) {
        this.ID = ID;
        this.text = text;
        this.author = author;
        this.date = date;

        if (activity != null) { this.activity = activity; }
        else { this.activity = new ArrayList<>(); }

        if (mentions != null) { this.mentions = mentions; }
        else { this.mentions = new ArrayList<>(); }
    }

    public Kweet(String text, User author, Date date, List<Activity> activity, ArrayList<User> mentions) {
        this.text = text;
        this.author = author;
        this.date = date;

        if (activity != null) { this.activity = activity; }
        else { this.activity = new ArrayList<>(); }

        if (mentions != null) { this.mentions = mentions; }
        else { this.mentions = new ArrayList<>(); }
    }
    //endregion

    //region Methods
    public boolean HasMention(String searchTerm) {
        String searchTermLower = searchTerm.toLowerCase();
        for (User user : mentions) {
            if (user.getUsername().toLowerCase() == searchTermLower) return true;
        }
        return false;
    }
    //endregion
}
