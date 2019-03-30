package RestResources;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import models.Account;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.given;

@RunWith(Arquillian.class)
public class AccountResourceTest {
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsDirectory("src/main/java")
                .addPackages(true, "DAL")
                .addPackages(true, "models")
                .addPackages(true, "Controllers")
                .addPackages(true, "RestResources")
                .addPackages(true, "Services")
                .addPackages(true, "com.airhacks")
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
                .addAsLibraries(files);

        // Show the deploy structure
        System.out.println(war.toString(true));
        return war;
    }

    @ArquillianResource
    URL basePath;

    @Test
    @RunAsClient
    public void getAccountBlackBox() {
        int accid = 1;
        String location = "kwetter/account/" + accid;
        Response response = given().contentType(ContentType.JSON).when().get(basePath + location);

        Account account = response.getBody().as(Account.class);
        Assert.assertEquals(accid, account.getID());
    }

    @Test
    @RunAsClient
    public void getAccountsBlackBox() {
        String location = "kwetter/accounts";
        Response response = given().contentType(ContentType.JSON).when().get(basePath + location);

        Account[] accounts = response.getBody().as(Account[].class);
        for(Account acc : accounts){
            Assert.assertTrue(acc.getUser() == null);
            System.out.println(acc.toString());
        }
    }

    @Test
    @RunAsClient
    public void LoginBlackBox(){
        String location = "kwetter/login";
        String username = "Henk";
        String password = "Henk";
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        params.add(password);

        Response response = given().contentType(ContentType.JSON).body(params).when().get(basePath + location);

        Account acc = response.getBody().as(Account.class);
        System.out.println(acc);
    }
}
