package Controllers;

import models.Profile;

import javax.ws.rs.*;

@Path("/ProfileController")
public class ProfileController {
    @GET
    @Path("/Profile/{userid}")
    @Produces("application/json")
    public Profile getProfile(@PathParam("userid") int a){
        System.out.println("amount: " + a);

        if(a == 0){
            return new Profile("test","test","test", "test");
        }
        else{
            return new Profile("p.png","hi im new","Netherlands", "LOL.com");
        }

    }
}
