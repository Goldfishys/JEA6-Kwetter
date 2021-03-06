package RestResources;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import models.Account;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.TransitiveStrategy;
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
        Maven.configureResolver()
                .withClassPathResolution(true)
                .withMavenCentralRepo(true)
                .fromFile("/path/to/settings.xml")
                .resolve("org.knowm.xchart:xchart:jar:3.5.2")
                .using(TransitiveStrategy.INSTANCE)
                .asList(File.class);

        File[] files= Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve()
                .withTransitivity()
                .asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsDirectory("src/main/java")
                .addPackages(true, "DAL")
                .addPackages(true, "models")
                .addPackages(true, "Controllers")
                .addPackages(true, "RestResources")
                .addPackages(true, "Services")
                .addPackages(true, "com.airhacks")
                .addPackages(true,"Websockets")
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
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
        //happy path met account dat bestaat
        int accid = 1;
        String location = "kwetter/account/" + accid;
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(basePath + location);

        Account account = response.getBody().as(Account.class);
        Assert.assertEquals(accid, account.getID());

        //unhappy path met account dat niet bestaat
        accid = 1000;
        location = "kwetter/account/" + accid;
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(basePath + location);
        System.out.println(response.getBody());
        Assert.assertEquals(204, response.getStatusCode());
    }

    @Test
    @RunAsClient
    public void getAccountsBlackBox() {
        String location = "kwetter/accounts";
        Response response = given().contentType(ContentType.JSON).when().get(basePath + location);

        Account[] accounts = response.getBody().as(Account[].class);
        for (Account acc : accounts) {
            Assert.assertNull(acc.getUser());
            System.out.println(acc.toString());
        }
    }

    @Test
    @RunAsClient
    public void LoginBlackBox() {
        String location = "kwetter/login";

        //Als eerste wordt er een poging gedaan om met een niet bestaande username en wachtwoord in te loggen
        ArrayList<String> params = new ArrayList<>();
        params.add("Henk");
        params.add("Henk");

        System.out.println("Base Path: " + basePath);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post(basePath + location);
        int returnCode = response.getStatusCode();
        Assert.assertEquals(204, returnCode);

        //Vervolgens wordt de juiste username met verkeerde wachtwoord ingevuld
        params = new ArrayList<>();
        params.add("User1");
        params.add("Henk");

        System.out.println("Base Path: " + basePath);
        response = given()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post(basePath + location);
        returnCode = response.getStatusCode();
        Assert.assertEquals(204, returnCode);

        //De laatste test, test een juiste username met een juist wachtwoord
        params = new ArrayList<>();
        params.add("User1");
        params.add("User1");

        System.out.println("Base Path: " + basePath);
        response = given()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post(basePath + location);
        String token = response.getBody().jsonPath().get("token");
        Assert.assertTrue(token.length() > 0);
    }
}
