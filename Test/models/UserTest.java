package models;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(Arquillian.class)
public class UserTest {
    Account acc1;
    Account acc2;
    KwetterService ks;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsDirectory("src/main/java")
                .addPackages(true, "DAL")
                .addPackages(true, "models");
    }

    @Before
    public void setUp() throws Exception {
        ks = new KwetterService();
        acc1 = ks.Register("Henkie");
    }

    @Test
    public void postKweet() {
        String sentence = "O hi mark!";
        acc1.getUser().PostKweet(sentence);
        Assert.assertEquals(1, acc1.getUser().getKweets().size());
        Assert.assertEquals(sentence, acc1.getUser().getKweets().get(0).getText());

        String sentenceLong = "Lkjgafnieu589gtunq90vu3awoirjfg09qfwi43gn9rujas;eosi5rgjq09mau3vw490uje5ungn983aupzw4z5m8mi;0k,gh5wey4wtgsw345ehw45ykuayw3h98g4bu5nw4895hthsuge985u3kuayw3h98g4bu5nw4895hthsuge985u3gwh98u698ahu4ejiujh896uh83";
        acc1.getUser().PostKweet(sentenceLong);
        Assert.assertEquals(1, acc1.getUser().getKweets().size());
    }

    @Test
    public void getRecentKweets() {
        String sentence = "Vogel";
        String sentence2 = "Koekje";

        for (int i = 0; i < 11; i++) {
            acc1.getUser().PostKweet(sentence);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 11; i++) {
            acc1.getUser().PostKweet(sentence2);
        }


        ArrayList<Kweet> recentKweets = acc1.getUser().GetRecentKweets();
        Assert.assertEquals(10, recentKweets.size());

        for (Kweet kweet : recentKweets) {
            Assert.assertNotEquals(sentence, kweet.getText());
        }
    }

    @Test
    public void updateUsername() {
        String newName = "Banjo";

        //test if can change
        Assert.assertNotEquals(newName, acc1.getUser().getUsername());
        acc1.getUser().UpdateUsername(newName);
        Assert.assertEquals(newName, acc1.getUser().getUsername());

        //test if behavior correct if new name is invalid
        acc1.getUser().UpdateUsername("");
        Assert.assertEquals(newName, acc1.getUser().getUsername());
    }

    @Test
    public void loadAndUpdateProfile() {
        //check current bio is empty
        Assert.assertEquals("", acc1.getUser().getProfile().getBio());

        //create bio on existing user and push update to database
        String bio = "Hi, ik ben een Banjo";
        acc1.getUser().UpdateProfile(bio, "Eindhoven", "www.Banjo.nl", "Banjo.png");

        //create new user with same id as last account
        acc2 = new Account(acc1.getID());
        acc2.LoadUser();

        //load profile and check if bio's match
        acc2.getUser().LoadProfile();
        Assert.assertEquals(bio, acc2.getUser().getProfile().getBio());
    }

    @Test
    public void followerTest(){
        acc2 = ks.Register("Guitar");

        //test adding follower
        acc1.getUser().AddFollower(acc2);
        acc1.getUser().AddFollower(acc2);
        Assert.assertEquals(1,acc1.getUser().getFollowers().size());
        Assert.assertEquals(1,acc2.getUser().getFollowing().size());

        //test removing followers
        acc1.getUser().RemoveFollower(acc2);
        acc1.getUser().RemoveFollower(acc2);
        Assert.assertEquals(0,acc1.getUser().getFollowers().size());
        Assert.assertEquals(0,acc2.getUser().getFollowing().size());
    }

    @Test
    public void timeLineTest(){
        ArrayList<Account> following = new ArrayList<>();

        //create users
        following.add(ks.Register("Drums"));
        following.add(ks.Register("Flute"));
        following.add(ks.Register("Piano"));
        following.add(ks.Register("Keyboard"));
        acc1 = ks.Register("MusicPlayer");

        Assert.assertEquals(0, acc1.getUser().GetTimeLine().size());

        //add acc1 as follower of all users & post 2 kweets for each acc
        for(Account account : following){
            User usr = account.getUser();
            usr.AddFollower(acc1);
            usr.PostKweet("Hi i am " + usr.getUsername());
            usr.PostKweet("I Like Music!");
        }

        acc1.getUser().PostKweet("Chellowwww");
        acc1.getUser().PostKweet("Chellowwww");

        //pull timeline for acc1, check if it has 10 kweets
        Assert.assertEquals(10, acc1.getUser().GetTimeLine().size());
    }
}
