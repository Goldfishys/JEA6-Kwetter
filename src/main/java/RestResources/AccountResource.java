package RestResources;

import Controllers.AccountController;
import models.Account;

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

    @GET
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Account Login(ArrayList<String> params){
        return ac.login(params.get(0), params.get(1));
    }

}
