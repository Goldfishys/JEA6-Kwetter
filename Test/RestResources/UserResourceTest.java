package RestResources;

import Controllers.UserController;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import models.User;
import models.dtomodels.UserDTO;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

@RunWith(Arquillian.class)
public class UserResourceTest {

    @Deployment
    public static WebArchive createDeployment() {
        File[] files= Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve()
                .withTransitivity()
                .asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(files)
                .addAsDirectory("src/main/java")
                .addPackages(true, "DAL")
                .addPackages(true, "models")
                .addPackages(true, "Controllers")
                .addPackages(true, "RestResources")
                .addPackages(true, "Services")
                .addPackages(true, "com.airhacks")
                .addPackages(true,"Websockets")
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        // Show the deploy structure
        System.out.println(war.toString(true));
        return war;
    }

    @ArquillianResource
    URL basePath;

    @Inject
    UserController uc;

    @Test
    @InSequence(1)
    public void GetUserWhiteBox() {
        int id = 2;
        User user = uc.GetUser(id);
        System.out.println(user.toString());
        System.out.println(user.getAccount().toString());
        Assert.assertEquals(id, user.getId());
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void GetUserBlackBox(){
        int userID = 1;
        String location = "kwetter/user/" + userID;
        Response response = given().contentType(ContentType.JSON).when().get(basePath + location);
        System.out.println("Body:" + response.getBody().asString());

        User user = response.getBody().as(User.class);
        System.out.println(user.toString());
        Assert.assertEquals(1, user.getId());
    }

    @Test
    @InSequence(3)
    public void GetFollowersWhiteBox(){
        int id = 2;
        User user = uc.GetUser(id);
        int n_followers = user.getFollowers().size();
        System.out.println("Followers: " + n_followers);

        List<UserDTO> followers = uc.GetFollowers(id);
        Assert.assertEquals(n_followers, followers.size());
    }

    @Test
    @RunAsClient
    @InSequence(4)
    public void GetFollowersBlackBox(){
        int userID = 2;
        String location = "kwetter/user/" + userID + "/followers";
        Response response = given().contentType(ContentType.JSON).when().get(basePath + location);
        UserDTO[] followers = response.getBody().as(UserDTO[].class);
        Assert.assertEquals(1, followers.length);

        userID = 2000;
        location = "kwetter/user/" + userID + "/followers";
        response = given().contentType(ContentType.JSON).when().get(basePath + location);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    @InSequence(5)
    public void GetFollowingWhiteBox(){
        int id = 1;
        User user = uc.GetUser(id);
        int n_following = user.getFollowing().size();
        System.out.println("Following: " + n_following);

        List<UserDTO> following = uc.GetFollowing(id);
        Assert.assertEquals(n_following, following.size());
    }

    @Test
    @RunAsClient
    @InSequence(6)
    public void GetFollowingBlackBox(){
        int userID = 1;
        String location = "kwetter/user/" + userID + "/following";
        Response response = given().contentType(ContentType.JSON).when().get(basePath + location);
        System.out.println(response.getBody().asString());
        UserDTO[] followers = response.getBody().as(UserDTO[].class);
        Assert.assertEquals(1, followers.length);

        userID = 2000;
        location = "kwetter/user/" + userID + "/following";
        response = given().contentType(ContentType.JSON).when().get(basePath + location);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    @InSequence(7)
    public void FollowUserWhiteBox(){
        //2 wants to follow 1
        int idToFollow = 1;
        int idFollower = 2;
        uc.FollowUser(idToFollow, idFollower);

        List<UserDTO> following = uc.GetFollowing(idFollower);
        Assert.assertEquals(1, following.size());

        List<UserDTO> followers = uc.GetFollowers(idToFollow);
        Assert.assertEquals(1, followers.size());
    }

    @Test
    @InSequence(8)
    public void UnFollowUserWhiteBox(){
        //2 doesnt want to follow 1 anymore
        int idToFollow = 1;
        int idFollower = 2;
        uc.UnFollowUser(idToFollow, idFollower);

        List<UserDTO> following = uc.GetFollowing(idFollower);
        Assert.assertEquals(0, following.size());

        List<UserDTO> followers = uc.GetFollowers(idToFollow);
        Assert.assertEquals(0, followers.size());
    }

    @Test
    @InSequence(9)
    @RunAsClient
    public void FollowUserBlackBox(){
        //2 wants to follow 1
        int idToFollow = 1;
        int idFollower = 2;
        String location = "kwetter/user/" + idFollower + "/follow/" + idToFollow;
        given().contentType(ContentType.JSON).when().patch(basePath + location).then().statusCode(204);

        location = "kwetter/user/" + idToFollow + "/followers";
        Response response = given().contentType(ContentType.JSON).when().get(basePath + location);
        System.out.println(response.getBody().asString());

        User[] followers = response.getBody().as(User[].class);
        Assert.assertEquals(1, followers.length);
    }

    @Test
    @InSequence(10)
    @RunAsClient
    public void UnFollowUserBlackBox(){
        //2 doesnt want to follow 1 anymore
        int idToFollow = 1;
        int idFollower = 2;
        String location = "kwetter/user/" + idFollower + "/unfollow/" + idToFollow;
        given().contentType(ContentType.JSON).when().patch(basePath + location).then().statusCode(204);

        location = "kwetter/user/" + idToFollow + "/followers";
        Response response = given().contentType(ContentType.JSON).when().get(basePath + location);
        System.out.println(response.getBody().asString());

        User[] followers = response.getBody().as(User[].class);
        Assert.assertEquals(0, followers.length);
    }

    @Test
    @RunAsClient
    @InSequence(11)
    public void UpdateUsernameBlackBox(){
        String username = "PraiseTheSun";
        int userID = 1;
        String location = "kwetter/user/" + userID;

        //check that response is user with new username
        Response response = given().contentType(ContentType.TEXT).body(username).when().patch(basePath + location);
        User user = response.getBody().as(User.class);
        System.out.println("Username: " + user.getUsername());
        Assert.assertEquals(user.getUsername(), username);

        //get username from db with new request to check it got persisted
        response = given().contentType(ContentType.JSON).when().get(basePath + location);
        user = response.getBody().as(User.class);
        Assert.assertEquals(user.getUsername(), username);

        //change username back to normal
        given().contentType(ContentType.TEXT).body("Henk").when().patch(basePath + location);
    }
}
