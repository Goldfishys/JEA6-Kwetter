package src.main.UnitTests;

import DAL.Implementations.Mock.AccountRepoMock;
import DAL.Interfaces.IAccount;
import models.Account;
import models.Group;
import models.Permission;
import models.Role;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class AccountTest {
    IAccount accRepo;
    Account acc1;
    Group userGroup;
    Group adminGroup;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(models.Account.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp() throws Exception {
        accRepo = new AccountRepoMock();

        //create permissions
        List<Permission> userPermissions = new ArrayList<Permission>(){{add(Permission.CreateOwnKweets); add(Permission.EditOwnKweets); add(Permission.DeleteOwnKweets);}};
        Role userRole = new Role("User", userPermissions);
        List<Permission> adminPermissions = new ArrayList<Permission>(){{add(Permission.CreateOwnKweets); add(Permission.EditOwnKweets); add(Permission.DeleteOwnKweets); add(Permission.EditAllKweets); add(Permission.DeleteAllKweets); add(Permission.EditPermissions);add(Permission.RemoveAccounts);}};
        Role adminRole = new Role("Admin", adminPermissions);

        //create groups
        Group userGroup = new Group("UserGroup", userRole);
        Group adminGroup = new Group("AdminGroup", adminRole);
        accRepo.CreateGroup(userGroup);
        accRepo.CreateGroup(adminGroup);
    }

    @Test
    public void JoinGroupTest(){
        //Test if adding user to group works
        Assert.assertEquals(0, acc1.groups.size());
        acc1.JoinGroup(userGroup);
        Assert.assertEquals(1, acc1.groups.size());

        //Test if adding user twice to the group doesn't work
        acc1.JoinGroup(userGroup);
        Assert.assertEquals(1, acc1.groups.size());

        //Text if adding user to multiple groups works
        acc1.JoinGroup(adminGroup);
        Assert.assertEquals(2, acc1.groups.size());
    }
}
