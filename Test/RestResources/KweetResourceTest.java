package RestResources;

import Controllers.AccountController;
import Controllers.KweetController;
import Controllers.RoleController;
import Controllers.UserController;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import models.*;
import models.dtomodels.KweetDTO;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.SortedSet;

import static com.jayway.restassured.RestAssured.given;

@RunWith(Arquillian.class)
public class KweetResourceTest {

    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
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
                .addPackages(true, "Websockets")
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(files);

        // Show the deploy structure
        System.out.println(war.toString(true));
        return war;
    }

    @Before
    public void setUp() throws Exception {
        //RestAssured.defaultParser = Parser.JSON;
    }

    @ArquillianResource
    URL basePath;

    @Inject
    KweetController kc;

    @Inject
    AccountController accountController;

    @Inject
    RoleController roleController;

    @Inject
    UserController userController;

    static int kweetID;

    @Test
    public void GenerateTestData() {

        int amountOfUsers = 20;
        //maak useres aan
        for (int i = 1; i <= amountOfUsers; i++) {
            //register new account
            Role userRole = roleController.getAllRoles().get(0);
            String username = "User" + i;
            Account acc = new Account(username, username);
            acc.setUser(new User(username, acc));
            acc.getUser().setProfile(new Profile("https://i0.wp.com/www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png?fit=256%2C256&quality=100&ssl=1"
                    , "hi im a user", "nijmegen", "https://www.gorillaz.com/"));
            accountController.registerAccount(acc);

            //assign user role
            roleController.updateRoles(acc.getID(), userRole);

            //post 10 kweets
            for (int x = 1; x <= 10; x++) {
                Kweet kweet = new Kweet("Kweet" + x, i, null, null);
                kc.PostKweet(kweet);
            }
        }

        //follow atleast 10 users
        for (int i = 1; i <= amountOfUsers; i++) {
            int index = i;
            for (int x = 0; x <= 10; x++) {
                if(index == 0) {
                    index = amountOfUsers;
                }
                userController.FollowUser(index, i);
                index--;
            }
        }
    }

    @Test
    @InSequence(1)
    public void PostKweetWhiteBox() {
//        for(int i =1; i <11; i++){
//            for(int x =1; x <11; x++){
//                Kweet kweet = new Kweet("Kweet" + x, i, null,null);
//                kc.PostKweet(kweet);
//            }
//        }

        //post new kweet happy path
        Kweet kweet = new Kweet("Test Kweet", 1, null, null);
        KweetDTO returnKweet = kc.PostKweet(kweet);
        Assert.assertEquals(kweet.getText(), returnKweet.getText());
        Assert.assertTrue(returnKweet.getID() > 0);

        //post a new kweet without text
        kweet = new Kweet("", 1, null, null);
        returnKweet = kc.PostKweet(kweet);
        Assert.assertNull(returnKweet);

        //post a new kweet with a null value as text
        kweet = new Kweet(null, 1, null, null);
        returnKweet = kc.PostKweet(kweet);
        Assert.assertNull(returnKweet);
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void PostKweetBlackBox() {
        //for user create kweet
        String location = "kwetter/kweet";
        String text = "TestMessage";
        Kweet kweet = new Kweet(text, 1);
        Response response = given().body(kweet).contentType(ContentType.JSON).when().post(basePath + location);

        //check response
        System.out.println("Body: " + response.getBody().asString());
        Assert.assertEquals(text, response.jsonPath().get("text"));
        int id = response.jsonPath().get("id");
        kweetID = id;
    }

    @Test
    @RunAsClient
    @InSequence(3)
    public void GetKweetBlackBox() {
        String location = "kwetter/kweet/" + kweetID;
        System.out.println("kwtid: " + kweetID);
        Response response = given().contentType(ContentType.JSON).get(basePath + location);

        System.out.println("body: " + response.getBody().asString());
        Kweet kweet = response.getBody().as(Kweet.class);
        Assert.assertEquals(kweetID, (int) response.jsonPath().get("id"));
    }

    @Test
    @InSequence(4)
    public void UpdateKweetWhitebox() throws Exception {
        int kweetid = 1;
        //get kweet
        Kweet kweet = new Kweet(kc.GetKweet(kweetid));
        String textupdate = "check JPA still works";

        //update kweet
        kweet.setText(textupdate);
        Kweet kweet2 = kc.UpdateKweet(kweet);
        System.out.println(kweet2.getText());

        //get kweet and assert texts
        KweetDTO kwt = kc.GetKweet(kweetid);
        Assert.assertEquals(textupdate, kwt.getText());
    }

    @Test
    @RunAsClient
    @InSequence(5)
    public void UpdateKweetBlackBox() {
        String getlocation = "kwetter/kweet/" + kweetID;
        String updatelocation = "kwetter/kweet";

        //get a kweet
        Response response = given().contentType(ContentType.JSON).get(basePath + getlocation);
        Kweet kweet = response.getBody().as(Kweet.class);

        //update the text of the kweet
        String textupdate = "Yay updated to a postive text :)";
        kweet.setText(textupdate);
        response = given().body(kweet).contentType(ContentType.JSON).put(basePath + updatelocation);
        System.out.println(response.getBody().asString());
        Kweet responseKweet = response.getBody().as(Kweet.class);
        Assert.assertEquals(textupdate, responseKweet.getText());

        //check that the update got put through with a new get
        response = given().contentType(ContentType.JSON).get(basePath + getlocation);
        Kweet updatedKweet = response.getBody().as(Kweet.class);
        Assert.assertEquals(textupdate, updatedKweet.getText());
    }

    @Test
    @InSequence(6)
    public void GetRecentKweetsWhiteBox() {
        SortedSet<KweetDTO> recentKweets = kc.GetRecentKweets(1);
        System.out.println(recentKweets.size());
        Assert.assertTrue(recentKweets.size() <= 10);
        for (KweetDTO kweet : recentKweets) {
            System.out.println("Text: " + kweet.getText() + " Created: " + kweet.getCreated());
        }
    }

    @Test
    @RunAsClient
    @InSequence(7)
    public void GetRecentKweetsBlackBox() {
        int userid = 1;
        String location = "kwetter/kweet/recentkweets/" + userid;
        Response response = given().contentType(ContentType.JSON).get(basePath + location);

        System.out.println(response.getBody().asString());
        KweetDTO[] recentKweets = response.getBody().as(KweetDTO[].class);

        Assert.assertTrue(recentKweets.length <= 10);
    }

    @Test
    @RunAsClient
    @InSequence(8)
    public void SearchKweetBlackBox() {
        String searchTerm = "test";
        String location = "kwetter/kweet/search/" + searchTerm;
        Response response = given().contentType(ContentType.JSON).get(basePath + location);

        Kweet[] foundKweets = response.getBody().as(Kweet[].class);
        System.out.println("size: " + foundKweets.length);
        for (Kweet kweet : foundKweets) {
            System.out.println("kweetText: " + kweet.getText());
            Assert.assertTrue(kweet.getText().toLowerCase().contains(searchTerm.toLowerCase()));
        }

        searchTerm = "@";
        location = "kwetter/kweet/search/" + searchTerm;
        response = given().contentType(ContentType.JSON).get(basePath + location);

        foundKweets = response.getBody().as(Kweet[].class);
        System.out.println("size: " + foundKweets.length);
        Assert.assertEquals(0, foundKweets.length);
        for (Kweet kweet : foundKweets) {
            System.out.println("kweetText: " + kweet.getText());
            Assert.assertTrue(kweet.getText().toLowerCase().contains(searchTerm.toLowerCase()));
        }
    }

    @Test
    @RunAsClient
    @InSequence(9)
    public void DeleteKweetBlackBox() {
        //get this kweet
        String location = "kwetter/kweet/" + kweetID;
        Response response = given().contentType(ContentType.JSON).get(basePath + location);
        Kweet kweet = response.getBody().as(Kweet.class);

        //delete the kweet
        response = given().contentType(ContentType.JSON).body(kweet.getID()).delete(basePath + location);
        Boolean deleted = response.getBody().as(Boolean.class);
        Assert.assertEquals(true, deleted);
    }

    @Test
    @InSequence(10)
    public void GetTimeLineWhiteBox() {
        SortedSet<KweetDTO> kweets = kc.GetTimeLine(1);
        Assert.assertEquals(20, kweets.size());
    }

    @Test
    @RunAsClient
    @InSequence(11)
    public void GetTimeLineBlackBox() {
        int accid = 1;
        String location = "kwetter/kweet/timeline/" + accid;
        Response response = given().contentType(ContentType.JSON).get(basePath + location);
        KweetDTO[] kweets = response.getBody().as(KweetDTO[].class);
        Assert.assertEquals(20, kweets.length);

        accid = 20000;
        location = "kwetter/kweet/timeline/" + accid;
        response = given().contentType(ContentType.JSON).get(basePath + location);
        kweets = response.getBody().as(KweetDTO[].class);
        Assert.assertEquals(0, kweets.length);
    }
}
