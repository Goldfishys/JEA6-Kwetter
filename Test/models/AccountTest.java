package models;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AccountTest {
    KwetterService ks;
    Account acc1;
    Account acc2;
    Group adminGroup;
    Group userGroup;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsDirectory("src/main/java")
                .addPackages(true,"DAL")
                .addPackages(true,"models");
    }

    @Before
    public void setUp() throws Exception {
        ks = new KwetterService();
        acc1 = ks.Register("Ben", "trick");
        acc2 = ks.Register("Olaf", "trick");
        adminGroup = new Group("AdminGroup",new Role());
        userGroup = new Group("UserGroup",new Role());
    }

    @Test
    public void joinGroup() {
        //test if user can join group
        Assert.assertEquals(0, userGroup.accounts.size());
        acc1.JoinGroup(userGroup);
        Assert.assertEquals(1, userGroup.accounts.size());

        //test if user can join same group twice
        acc1.JoinGroup(userGroup);
        Assert.assertEquals(1, userGroup.accounts.size());

        //test if user can join more than 1 group
        acc1.JoinGroup(adminGroup);
        Assert.assertEquals(1, userGroup.accounts.size());
        Assert.assertEquals(1, adminGroup.accounts.size());
    }

    @Test
    public void leaveGroup() {
        //test if user can leave group he joined
        acc1.JoinGroup(userGroup);
        acc1.JoinGroup(adminGroup);

        acc2.JoinGroup(userGroup);
        acc2.JoinGroup(adminGroup);
        Assert.assertEquals(2, adminGroup.accounts.size());
        Assert.assertEquals(2, userGroup.accounts.size());

        acc2.LeaveGroup(adminGroup);
        Assert.assertEquals(1, adminGroup.accounts.size());

        //test if user can leave the same group twice when there are multiple groups
        acc2.LeaveGroup(adminGroup);
        Assert.assertEquals(1, adminGroup.accounts.size());
    }

    @Test
    public void loadUser() {
        int id = acc2.getID();
        Account acc3 = new Account(id);
        Assert.assertEquals(null, acc3.getUser());

        acc3.LoadUser();
        Assert.assertEquals(acc2.getUser().getAccount().getID(), acc3.getUser().getAccount().getID());
    }
}
