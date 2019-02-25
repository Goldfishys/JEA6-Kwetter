package models;

import DAL.Database;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class KweetTest {
    Account acc1;
    Account acc2;
    Account acc3;
    KwetterService ks;
    Group adminGroup;
    Group userGroup;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsDirectory("src/main/java")
                .addPackages(true, "DAL")
                .addPackages(true, "models");
    }

    @Before
    public void setUp() throws Exception {
        //create kwetterservice and accounts
        ks = new KwetterService();
        acc1 = ks.Register("Henkie");
        acc2 = ks.Register("Paulie");
        acc3 = ks.Register("MOD");

        //set permissions for accounts

        adminGroup = new Group("AdminGroup",new Role("AdminGroup", new ArrayList<>(Arrays.asList(Permission.DeleteAllKweets, Permission.DeleteOwnKweets))));
        userGroup = new Group("UserGroup",new Role("UserGroup",  new ArrayList<>(Arrays.asList(Permission.DeleteOwnKweets))));
        Database.getInstance().groupRepo.CreateGroup(adminGroup);
        Database.getInstance().groupRepo.CreateGroup(userGroup);
        adminGroup.AddAccount(acc3);
        userGroup.AddAccount(acc1);
        userGroup.AddAccount(acc2);
    }

    @Test
    public void deleteKweet() {
        //acc1 post 2 kweets
        acc1.getUser().PostKweet("Tea");
        acc1.getUser().PostKweet("Bingo!");
        assertEquals(2, Database.getInstance().accountRepo.GetAccountByID(acc1.getID()).getUser().getKweets().size());

        //acc1 removes kweet 1
        Kweet kweet1 = acc1.getUser().getKweets().get(1);
        kweet1.DeleteKweet(acc1.getID());
        assertEquals(1, Database.getInstance().accountRepo.GetAccountByID(acc1.getID()).getUser().getKweets().size());

        //acc2 without permissions tries to delete kweet, doesnt work
        Kweet kweet2 = acc1.getUser().getKweets().get(0);
        kweet2.DeleteKweet(acc2.getID());
        assertEquals(1, Database.getInstance().accountRepo.GetAccountByID(acc1.getID()).getUser().getKweets().size());

        //acc3 is MODERATOR and deletes kweet, which works
        kweet2.DeleteKweet(acc3.getID());
        assertEquals(0, Database.getInstance().accountRepo.GetAccountByID(acc1.getID()).getUser().getKweets().size());
    }
}
