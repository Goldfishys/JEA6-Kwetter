package Websockets;

import Controllers.KweetController;
import Controllers.UserController;
import com.google.gson.Gson;
import models.Kweet;
import models.User;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Singleton
@Startup
@ServerEndpoint("/websocket/{userid}")
public class KweetWebsocket {

    private HashMap<Integer, Session> user_Session;

    @Inject
    private KweetController kc;

    @Inject
    private UserController us;

    public KweetWebsocket() {
        System.out.println("Starting the websocket on the server!");
        user_Session = new HashMap<>();
    }

    @OnOpen
    public void open(@PathParam("userid") int userid, Session session) {
        System.out.println("#OnOpen: " + session.toString());
        user_Session.put(userid, session);
    }

    @OnClose
    public void close(Session session) {
        System.out.println("#OnClose: " + session.toString());
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("#OnError: " + error);
    }

    @OnMessage
    public void handleMessage(String message) {
        System.out.println("Message: " + message);
        if(message != "") {
            System.out.println("#OnMessage: " + message);
            Gson gson = new Gson();
            Kweet kweet = gson.fromJson(message, Kweet.class);

            System.out.println("Created kweet from message, posting it in the database");
            Kweet kwt2 = kc.PostKweet(kweet);

            System.out.println("Kweet has been saved, sending the new kweet back to the original session");
            BroadcastPostedKweet(kwt2);
        }
        else{
            System.out.println("Wowa your message is empty!");
        }
    }

    private void BroadcastPostedKweet(Kweet kwt2) {
        //send kweet to the author
        sendMessage(getSession(kwt2.getAuthor()), kwt2);

        //send kweet to his followers that are logged in
        ArrayList<User> users = us.GetFollowers(kwt2.getAuthor());
        for(User user : users){
            Session session = getSession(user.getId());
            if(session != null){
                sendMessage(getSession(user.getId()), kwt2);
            }
        }
    }

    private Session getSession(int userid){
        return user_Session.get(userid);
    }

    private void sendMessage(Session session, Object message){
        Gson gson = new Gson();
        String json = gson.toJson(message);
        try {
            session.getBasicRemote().sendText(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
