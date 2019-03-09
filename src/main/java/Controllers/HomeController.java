package Controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/Kwetter")
public class HomeController {

    @GET
    @Path("/TimeLine")
    @Produces("application/json")
    public String getClichedMessage() {
        return "Kweet Kweet!";
    }
}
