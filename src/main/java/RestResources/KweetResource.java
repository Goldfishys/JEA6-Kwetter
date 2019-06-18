package RestResources;

import Controllers.KweetController;
import com.airhacks.JWTTokenNeeded;
import models.DTOmodels.KweetDTO;
import models.Kweet;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Path("/kweet")
public class KweetResource {

    @Inject
    KweetController kc;

    @GET
    @Path("/search/{SearchTerm}")
    @Produces("application/json")
    public List<KweetDTO> SearchKweets(@PathParam("SearchTerm") String SearchTerm) {
        List<KweetDTO> kwts = kc.SearchKweets(SearchTerm, false);
        System.out.println("found your kweets :)");
        return kwts;
    }

    @POST
    @Path("")
//    @JWTTokenNeeded
    @Consumes("application/json")
    @Produces("application/json")
    public KweetDTO PostKweet(Kweet kweet) {
        return kc.PostKweet(kweet);
    }

    @GET
    @Path("/{kweetID}")
    @Produces("application/json")
    public KweetDTO GetKweet(@PathParam("kweetID") int kweetID, @Context UriInfo uriInfo) {
        KweetDTO kwt = kc.GetKweet(kweetID);

        List<Link> links = new ArrayList<>();
        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder().path(String.valueOf(kwt.getID())))
                .rel("self").type("GET").build();
        links.add(self);

        Link delete = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder().path(String.valueOf(kwt.getID())))
                .rel("delete").type("DELETE").build();
        links.add(delete);

        Link update = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder().path(String.valueOf(kwt.getID())))
                .rel("update").type("PUT").build();
        links.add(update);
        kwt.set_links(links);

        return kwt;
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
    public SortedSet<KweetDTO> GetRecentKweets(@PathParam("accountID") int accountID) {
        return kc.GetRecentKweets(accountID);
    }

    @GET
//    @JWTTokenNeeded
    @Path("/timeline/{accountID}")
    @Produces("application/json")
    public SortedSet<KweetDTO> GetTimeLine(@PathParam("accountID") int accountID) {
        return kc.GetTimeLine(accountID);
    }

    @GET
    @Path("/date/{accountID}")
    @Produces("application/json")
    public KweetDTO GetTimeLineDate(@PathParam("accountID") int accountID) {
        return kc.GetTimeLine(accountID).first();
    }


}
