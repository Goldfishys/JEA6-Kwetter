package models;

import DAL.Database;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class KwetterServiceTest {
    Database database;
    KwetterService ks;
    Account acc1;
    Account acc2;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsDirectory("src/main/java")
                .addPackages(true,"DAL")
                .addPackages(true,"models");
    }

    @Before
    public void setUp() throws Exception {
        database = Database.getInstance();
        ks = new KwetterService();
    }

    @Test
    public void register() {
        Assert.assertEquals(0, database.accounts.size());
        ks.Register("Bennie");
        Assert.assertEquals(1, database.accounts.size());
        ks.Register("Bennie");
        Assert.assertEquals(2, database.accounts.size());

        Assert.assertNotEquals(database.accounts.get(0), database.accounts.get(1));
    }

    @Test
    public void searchKweets() {
        acc1 = ks.Register("Dirk");
        acc2 = ks.Register("Henk");

        acc1.getUser().PostKweet("Hallo @Henk !");
        acc2.getUser().PostKweet("Hallo, welcome @Dirk !");
        acc1.getUser().PostKweet("Hou jij ook van vis? @Henk");

        System.out.println(ks.SearchKweets("Hallo", false));

        //check ofdat test iets kan vinden
        Assert.assertEquals(2, ks.SearchKweets("Hallo", false).size());

        //check dat kweets altijd gevonden worden ook als lower case is
        Assert.assertEquals(1, ks.SearchKweets("vis", false).size());
        Assert.assertEquals(1, ks.SearchKweets("Vis", false).size());
    }
}
