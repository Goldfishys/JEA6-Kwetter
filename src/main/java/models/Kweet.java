package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import models.dtomodels.KweetDTO;

import javax.persistence.*;
import javax.ws.rs.core.Link;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Kweet")
public class Kweet implements Comparable<Kweet> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDkweet")
    @JsonProperty("id")
    private int ID;

    @Column(name = "KweetText")
    private String text;

    @Column(name = "Kweet_IDaccount")
    private int author;

    @Column
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime created;

    @Transient
    private List<Activity> activity;
    @Transient
    private List<User> mentions;

    @PrePersist
    protected void onCreate() {
        setCreated(LocalDateTime.now());
    }

    @Transient
    private List<Link> _links;

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

    public List<User> getMentions() {
        return mentions;
    }

    public void setMentions(ArrayList<User> mentions) {
        this.mentions = mentions;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<Link> get_links() {
        return _links;
    }

    public void set_links(List<Link> _links) {
        this._links = _links;
    }

    //endregion

    //region Constructors
    public Kweet() {
        this.activity = new ArrayList<>();
        this.mentions = new ArrayList<>();
        _links = new ArrayList<>();
    }

    public Kweet(KweetDTO kweetDTO) {
        this.ID = kweetDTO.getID();
        this.text = kweetDTO.getText();
        this.author = kweetDTO.getAuthorID();
        this.created = kweetDTO.getCreated();
    }

    public Kweet(String text, int author) {
        this.text = text;
        this.author = author;
        this.activity = new ArrayList<>();
        this.mentions = new ArrayList<>();
        _links = new ArrayList<>();
    }

    public Kweet(String text, int author, List<Activity> activity, List<User> mentions) {
        this.ID = 0;
        this.text = text;
        this.author = author;
        _links = new ArrayList<>();

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

    public Kweet(int ID, String text, int author, LocalDateTime date, List<Activity> activity, List<User> mentions) {
        this.ID = ID;
        this.text = text;
        this.author = author;
        setCreated(date);
        _links = new ArrayList<>();

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
            if (user.getUsername().toLowerCase().equals(searchTermLower)) return true;
        }
        return false;
    }

    public boolean IsValid() {
        if (text != null && text != "" && text.length() <= 140
                && author > 0) {
            return true;
        }
        return false;
    }

    public void Update(Kweet kweet) {
        if (kweet.IsValid() && kweet.getID() == this.getID()) {
            this.text = kweet.text;
            this.mentions = kweet.mentions;
            this.activity = kweet.activity;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Kweet && obj.hashCode() == this.hashCode()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        hashcode += this.getAuthor() * 33;
        hashcode += this.getCreated().hashCode() * 33;
        hashcode += this.getText().hashCode() * 33;
        hashcode += this.getActivity().hashCode() * 33;
        hashcode += this.getMentions().hashCode() * 33;
        return hashcode;
    }

    @Override
    public int compareTo(Kweet kweet) {
        if (getCreated().isBefore(kweet.getCreated())) return 1;
        else if (getCreated().isAfter(kweet.getCreated())) return -1;
        else return 0;
    }
    //endregion


}
