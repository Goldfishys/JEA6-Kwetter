package models;

import DAL.Database;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;
import java.util.List;
import java.util.Properties;

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
                .addPackages(true,"models")
                .addAsResource("META-INF/persistence.xml");
    }

    @Before
    public void setUp() throws Exception {
        //database = Database.getInstance();
        //ks = new KwetterService();
    }

    @Test
    public void EMTest() throws Exception {
        String persistenceUnitName = "Hibernate-Persistence";

        EntityManagerFactory emf = null;
        PersistenceProviderResolver resolver = PersistenceProviderResolverHolder.getPersistenceProviderResolver();
        System.out.println("resolver : " + resolver);

        List<PersistenceProvider> providers = resolver.getPersistenceProviders();
        System.out.println("providers : " + providers.size());

        for (PersistenceProvider provider : providers) {
            //throw new Exception("provider name: " + provider);
            System.out.println("provider name: " + provider);
            //throw new Exception(""+provider.createEntityManagerFactory(persistenceUnitName, null));
            emf = provider.createEntityManagerFactory(persistenceUnitName, null);
            if (emf != null) {
                break;
            }
        }
        if (emf == null) {
            throw new PersistenceException("No Persistence provider for EntityManager named " + persistenceUnitName);
        }
    }

    @Test
    public void register() {
        Assert.assertEquals(0, database.accounts.size());
        ks.Register("Bennie", "trick");
        Assert.assertEquals(1, database.accounts.size());
        ks.Register("Bennie", "trick");
        Assert.assertEquals(2, database.accounts.size());

        Assert.assertNotEquals(database.accounts.get(0), database.accounts.get(1));
    }

    @Test
    public void searchKweets() {
        acc1 = ks.Register("Dirk", "trick");
        acc2 = ks.Register("Henk", "trick");

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
