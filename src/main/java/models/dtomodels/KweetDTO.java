package models.dtomodels;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import models.Kweet;

import javax.ws.rs.core.Link;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class KweetDTO implements Comparable<KweetDTO> {

    //region properties
    private int id;
    private String text;
    private int authorID;
    private String authorUsername;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime created;
    private List<Link> _links;
    //endregion


    //region get/set
    public void set_links(List<Link> _links) {
        this._links = _links;
    }

    public int getID() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public List<Link> get_links() {
        return _links;
    }

    //endregion


    //region constructors
    public KweetDTO(){
        this._links = new ArrayList<>();
    }

    public KweetDTO(int ID, String text, int authorID, String authorUsername, LocalDateTime created) {
        this.id = ID;
        this.text = text;
        this.authorID = authorID;
        this.authorUsername = authorUsername;
        this.created = created;
    }

    public KweetDTO(Kweet kweet, String username){
        this.id = kweet.getID();
        this.text = kweet.getText();
        this.authorID = kweet.getAuthor();
        this.authorUsername = username;
        this.created = kweet.getCreated();
    }

    @Override
    public int compareTo(KweetDTO kweetDTO) {
        if (getCreated().isBefore(kweetDTO.getCreated())) return 1;
        else if (getCreated().isAfter(kweetDTO.getCreated())) return -1;
        else return 0;
    }
    //endregion
}
