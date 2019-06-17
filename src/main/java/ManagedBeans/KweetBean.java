package ManagedBeans;

import Controllers.KweetController;
import models.Kweet;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@ViewScoped
public class KweetBean implements Serializable {

    @Inject
    private KweetController kc;

    //region properties
    private String searchTerm;
    private ArrayList<Kweet> kweets;
    //endregion

    //region get/set
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public ArrayList<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(ArrayList<Kweet> kweets) {
        this.kweets = kweets;
    }

    //endregion

    public KweetBean() {
        kweets = new ArrayList<>();
    }

    public void SearchKweets(){
        System.out.println("starting search method");
        if(searchTerm != null && searchTerm != "") {
           kweets = kc.SearchKweets(searchTerm,false);
            for (Kweet kweet : kweets) {
                System.out.println(kweet.toString());
            }
        }
        System.out.println("finished search method");
    }

    public void DeleteKweet(int kweetid){
        System.out.println("starting delete method");
        if(kweetid != 0) {
            System.out.println("Deleting Kweet");
            kc.DeleteKweet(kweetid);
        }
        System.out.println("finished delete method");
    }
}
