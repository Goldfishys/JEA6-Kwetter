package Controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/Kwetter")
public class HomeController {

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Kweet Kweet!";
    }
}