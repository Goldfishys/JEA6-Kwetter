package RestResources;

import Controllers.UserController;
import models.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;

@Path("/user")
public class UserResource {

    @Inject
    UserController uc;

    @GET
    @Path("/{userID}")
    @Produces("application/json")
    public User GetUser(@PathParam("userID") int userID){
        return uc.GetUser(userID);
    }

    @GET
    @Path("/{userID}/followers")
    @Produces("application/json")
    public ArrayList<User> GetFollowers(@PathParam("userID") int userID){
        return uc.GetFollowers(userID);
    }


    @GET
    @Path("/{userID}/followers")
    @Produces("application/json")
    public ArrayList<User> GetFollowing(@PathParam("userID") int userID){
        return null;
    }


    @PATCH
    @Path("/{userID}")
    @Consumes("text/plain")
    @Produces("application/json")
    public User UpdateUsername(@PathParam("userID") int userID, String username){
        return uc.UpdateUsername(userID, username);
    }
}
