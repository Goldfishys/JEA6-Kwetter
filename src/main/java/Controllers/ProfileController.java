package Controllers;

import models.Profile;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/ProfileController")
public class ProfileController {
    @GET
    @Path("/Profile/{userid}")
    @Produces("text/plain")
    public Profile getProfile(@FormParam("userid") int a){
        if(a == 0){
            return new Profile("test","test","test", "test");
        }
        else{
            return new Profile("p.png","hi im new","Netherlands", "LOL.com");
        }

    }
}
