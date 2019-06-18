package RestResources;

import Controllers.AccountController;
import models.Account;
import models.DTOmodels.JWTTokenDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Path("")
public class AccountResource {

    @Inject
    AccountController ac;

    @GET
    @Path("/account/{accountID}")
    @Produces("application/json")
    public Account GetAccount(@PathParam("accountID") int accountID){
        return ac.GetAccount(accountID);
    }

    @GET
    @Path("/accounts")
    @Produces("application/json")
    public List<Account> GetAccounts(){
        return ac.GetAccounts();
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public JWTTokenDTO Login(ArrayList<String> args){
        System.out.println("Size: " + args.size());
        if(args.size() > 1) {
            return ac.login(args.get(0), args.get(1));
        }
        else {
            return null;
        }
    }

}
