package RestResources;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import models.Profile;
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

import static com.jayway.restassured.RestAssured.given;

@RunWith(Arquillian.class)
public class ProfileResourceTest {

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
    public void getProfileBlackBox() {
        String location = "kwetter/profile/1";
        Response response = given().contentType(ContentType.JSON).when().get(basePath + location);
        Profile profile = response.getBody().as(Profile.class);
        System.out.println(profile.toString());
        Assert.assertEquals(Profile.class, profile.getClass());
    }

    @Test
    @RunAsClient
    public void UpdateProfileBlackBox() {
        //update a profile
        String location = "kwetter/profile/1";
        String bio = "Oh hi marc!";
        String bio2 = "Hi there, im Henk";
        Profile profile = new Profile("picture.png", bio, "Eindhoven", "www.HIMARC.com");
        given().contentType(ContentType.JSON).when().body(profile).put(basePath+location);

        //get that profile with a new get
        Response response = given().when().get(basePath + location);
        Profile reponseProfile = response.getBody().as(profile.getClass());

        //check that the GET bio is the same as the input bio in the PATCH
        System.out.println(profile.hashCode() + " - " + reponseProfile.hashCode());
        Assert.assertTrue(profile.equals(reponseProfile));

        //put bio back to normal
        profile = new Profile("picture.png", bio2, "Eindhoven", "www.HIMARC.com");
        response = given().contentType(ContentType.JSON).when().body(profile).put(basePath+location);
        Profile prof2 = response.getBody().as(Profile.class);
        System.out.println(profile.hashCode() + " - " + prof2.hashCode());
        Assert.assertTrue(profile.equals(prof2));
    }
}
