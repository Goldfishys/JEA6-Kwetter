package RestResources;

import Controllers.ProfileController;
import models.Profile;
import models.dtomodels.ProfileDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/profile")
public class ProfileResource {

    @Inject
    private ProfileController pc;

    @GET
    @Path("/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProfileDTO getProfile(@PathParam("userid") int userid){
        return pc.GetProfile(userid);
    }

    @PUT
    @Path("/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Profile updateProfile(@PathParam("userid") int userid, final Profile profile){
        System.out.println("updated the profile!");
        return pc.UpdateProfile(userid, profile);
    }

}
