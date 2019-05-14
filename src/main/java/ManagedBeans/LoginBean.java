package ManagedBeans;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.acl.Group;
import java.util.Enumeration;
import java.util.Iterator;

@Named
@ApplicationScoped
public class LoginBean {

    //region properties
    private String username;
    private String password;
    //endregion

    //region get/set
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //endregion

    public LoginBean() {
        System.out.println("created instance of LoginBean");
    }

    //region methods
    public String Login() {
        System.out.println("Found login method");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)
                context.getExternalContext().getRequest();
        try {
            System.out.println("Username: " + this.username + " - Password: " + this.password);
            request.login(this.username, this.password);
            System.out.println("Principals: " + request.getUserPrincipal().getName());
            System.out.println("henk: " + request.isUserInRole("henk"));
            System.out.println("Admin: " + request.isUserInRole("Admin"));
            System.out.println("Moderator: " + request.isUserInRole("Moderator"));
            System.out.println("User: " + request.isUserInRole("User"));
            printRoles();
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed."));
            return "Error.html";
        }
        return "Kweets.xhtml";
    }

    private void printRoles() {
        try {
            InitialContext ic = new InitialContext();
            Subject subject = (Subject) ic.lookup("java:comp/env/security/subject");
// To list the Principals contained in the Subject...
            Iterator principals;
// To get the roles (the instance of java.security.acl.Group in the list of Principals)
            principals = subject.getPrincipals(java.security.acl.Group.class).iterator();
            if (principals.hasNext()) {
                Group roles = (Group) principals.next();
                Enumeration roleEnum = roles.members();
                while (roleEnum.hasMoreElements()) {
                    System.out.println("Role: " + roleEnum.nextElement());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)
                context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
    }
    //endregion
}
