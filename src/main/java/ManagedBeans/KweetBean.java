package ManagedBeans;

import Controllers.KweetController;
import models.dtomodels.KweetDTO;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class KweetBean implements Serializable {

    @Inject
    private KweetController kc;

    //region properties
    private String searchTerm;
    private List<KweetDTO> kweets;
    //endregion

    //region get/set
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<KweetDTO> getKweets() {
        return kweets;
    }

    public void setKweets(List<KweetDTO> kweets) {
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
            for (KweetDTO kweet : kweets) {
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
