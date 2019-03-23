package models;

import Jackson.JsonDateDeserializer;
import Jackson.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Kweet")
public class Kweet implements Comparable<Kweet>{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "IDkweet")
    @JsonProperty("id")
    private int ID;

    @Column(name = "KweetText")
    private String text;

    @Column(name = "Kweet_IDaccount")
    private int author;

    @Column
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime created;

    @Transient
    private List<Activity> activity;
    @Transient
    private ArrayList<User> mentions;

    @PrePersist
    protected void onCreate() {
        setCreated(LocalDateTime.now());
    }

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

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
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

    public LocalDateTime getCreated() {
        return created;
//        if(created != null) {
//            created = created.replace('T', ' ');
//            String pattern = "yyyy-MM-dd HH:mm:ss";
//            if (created.length() > 19) {
//                pattern += ".";
//                for (int i = created.length() - 20; i > 0; i--) {
//                    pattern += "S";
//                }
//            }
//            LocalDateTime ldt = LocalDateTime.parse(created, DateTimeFormatter.ofPattern(pattern));
//            return ldt;
//        }
//        else{
//            return null;
//        }
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
        //this.created = created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
    //endregion

    //region Constructors
    public Kweet() {
        this.activity = new ArrayList<>();
        this.mentions = new ArrayList<>();
    }

    public Kweet(String text, int author, List<Activity> activity, ArrayList<User> mentions) {
        this.ID = 0;
        this.text = text;
        this.author = author;

        if (activity != null) {
            this.activity = activity;
        } else {
            this.activity = new ArrayList<>();
        }

        if (mentions != null) {
            this.mentions = mentions;
        } else {
            this.mentions = new ArrayList<>();
        }
    }

    public Kweet(int ID, String text, int author, LocalDateTime date, List<Activity> activity, ArrayList<User> mentions) {
        this.ID = ID;
        this.text = text;
        this.author = author;
        setCreated(date);

        if (activity != null) {
            this.activity = activity;
        } else {
            this.activity = new ArrayList<>();
        }

        if (mentions != null) {
            this.mentions = mentions;
        } else {
            this.mentions = new ArrayList<>();
        }
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

    public boolean IsValid(){
        if(text != null && text != "" && text.length() <= 140
                && author > 0){
            return true;
        }
        return false;
    }

    public void Update(Kweet kweet) {
        if(kweet.IsValid() && kweet.getID() == this.getID()){
            this.text = kweet.text;
            this.mentions = kweet.mentions;
            this.activity = kweet.activity;
        }
    }

//    public void DeleteKweet(int deleterAccountID) {
//        if (this.getAuthor().getAccount().getID() == deleterAccountID) {
//            Database.getInstance().kweetRepo.RemoveKweet(this);
//        } else {
//            if (Database.getInstance().groupRepo.HasPermission(deleterAccountID, Permission.DeleteAllKweets)) {
//                Database.getInstance().kweetRepo.RemoveKweet(this);
//            }
//        }
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Kweet) {
            if(obj.hashCode() == this.hashCode())return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hashcode = 0;
        hashcode += this.getAuthor() * 33;
        hashcode += this.getCreated().hashCode() * 33;
        hashcode += this.getText().hashCode() * 33;
        hashcode += this.getActivity().hashCode() * 33;
        hashcode += this.getMentions().hashCode() * 33;
        return hashcode;
    }

    @Override
    public int compareTo(Kweet kweet){
        if(getCreated().isBefore(kweet.getCreated())) return 1;
        else if(getCreated().isAfter(kweet.getCreated())) return -1;
        else return 0;
    }
    //endregion


}
