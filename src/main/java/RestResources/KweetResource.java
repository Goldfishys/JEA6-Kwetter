package RestResources;

import Controllers.KweetController;
import com.airhacks.JWTTokenNeeded;
import models.Kweet;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.TreeSet;

@Path("/kweet")
public class KweetResource {

    @Inject
    KweetController kc;

    @GET
    @Path("/search/{SearchTerm}")
    @Produces("application/json")
    public ArrayList<Kweet> SearchKweets(@PathParam("SearchTerm") String SearchTerm) {
        ArrayList<Kweet> kwts = kc.SearchKweets(SearchTerm, false);
        System.out.println("found your kweets :)");
        return kwts;
    }

    @POST
    @Path("")
    @JWTTokenNeeded
    @Consumes("application/json")
    @Produces("application/json")
    public Kweet PostKweet(Kweet kweet) {
        return kc.PostKweet(kweet);
    }

    @GET
    @Path("/{kweetID}")
    @Produces("application/json")
    public Kweet GetKweet(@PathParam("kweetID") int kweetID) {
        return kc.GetKweet(kweetID);
    }

    @PUT
    @Path("")
    @Consumes("application/json")
    @Produces("application/json")
    public Kweet UpdateKweet(Kweet kweet) {
        return kc.UpdateKweet(kweet);
    }

    @DELETE
    @Path("/{kweetID}")
    @Produces("application/json")
    public boolean DeleteKweet(@PathParam("kweetID") int kweetID) {
        return kc.DeleteKweet(kweetID);
    }

    @GET
    @Path("/recentkweets/{accountID}")
    @Produces("application/json")
    public TreeSet<Kweet> GetRecentKweets(@PathParam("accountID") int accountID){
        return kc.GetRecentKweets(accountID);
    }

    @GET
    @JWTTokenNeeded
    @Path("/timeline/{accountID}")
    @Produces("application/json")
    public TreeSet<Kweet> GetTimeLine(@PathParam("accountID") int accountID, @Context UriInfo uriInfo){
        TreeSet<Kweet> kweets = kc.GetTimeLine(accountID);
        kweets.stream().forEach(p ->{
            Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder().path(String.valueOf(p.getID())))
                    .rel("self").build();
            p.get_links().add(self);
            Link delete = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder().path(String.valueOf(p.getID())))
                    .rel("delete").build();
            p.get_links().add(delete);
        });
        return kweets;
    }

    @GET
    @Path("/date/{accountID}")
    @Produces("application/json")
    public Kweet GetTimeLineDate(@PathParam("accountID") int accountID){
        return kc.GetTimeLine(accountID).first();
    }


}
