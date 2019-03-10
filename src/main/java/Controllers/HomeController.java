package Controllers;

import models.Kweet;
import models.KwetterService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;

@Path("/Kwetter")
public class HomeController {

    @Inject
    KwetterService ks;

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Kweet Kweet!";
    }

    @GET
    @Path("/search/{SearchTerm}")
    @Produces("application/json")
    public ArrayList<Kweet> search(@PathParam("SearchTerm") String SearchTerm){
        System.out.println(SearchTerm);
        return ks.SearchKweets(SearchTerm, false);
    }


}