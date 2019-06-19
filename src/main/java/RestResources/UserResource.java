package RestResources;

import Controllers.UserController;
import models.dtomodels.UserDTO;
import models.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<UserDTO> GetFollowers(@PathParam("userID") int userID){
        return uc.GetFollowers(userID);
    }


    @GET
    @Path("/{userID}/following")
    @Produces("application/json")
    public List<UserDTO> GetFollowing(@PathParam("userID") int userID){
        return uc.GetFollowing(userID);
    }


    @PATCH
    @Path("/{userID}")
    @Consumes("text/plain")
    @Produces("application/json")
    public User UpdateUsername(@PathParam("userID") int userID, String username){
        return uc.UpdateUsername(userID, username);
    }

    //2 wants to follow 1
    //userid = 2 acc id = 1
    @PATCH
    @Path("/{userID}/follow/{accountID}")
    public void FollowUser(@PathParam("userID") int userID, @PathParam("accountID") int accountID){
        uc.FollowUser(accountID, userID);
    }

    @PATCH
    @Path("/{userID}/unfollow/{accountID}")
    public void UnFollowUser(@PathParam("userID") int userID, @PathParam("accountID") int accountID){
        uc.UnFollowUser(accountID, userID);
    }
}
