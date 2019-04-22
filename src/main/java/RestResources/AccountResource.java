package RestResources;

import Controllers.AccountController;
import models.Account;
import models.JwtToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;

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
    public ArrayList<Account> GetAccounts(){
        return ac.GetAccounts();
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public JwtToken Login(ArrayList<String> args){
        System.out.println("Size: " + args.size());
        System.out.println(args.get(0));
        System.out.println(args.get(1));
        return ac.login(args.get(0), args.get(1));
    }

}
