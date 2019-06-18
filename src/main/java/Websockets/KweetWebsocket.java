package Websockets;

import Controllers.KweetController;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.DTOmodels.KweetDTO;
import models.Kweet;
import models.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Singleton
@Startup
@ServerEndpoint("/websocket/{userid}")
public class KweetWebsocket {

    private HashMap<Integer, Session> user_Session;

    @Inject
    private KweetController kc;


    @PostConstruct
    public void startWebsocket() {
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
        user_Session.remove(session);
        System.out.println("#OnClose: " + session.toString());
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("#OnError: " + error);
    }

    @OnMessage
    public void handleMessage(String message) {
        System.out.println("Message: " + message);
        if (message != "") {
            System.out.println("#OnMessage: " + message);
            ObjectMapper mapper = new ObjectMapper();
            try {
                Kweet kweet = mapper.readValue("{'name' : 'mkyong'}", Kweet.class);
                System.out.println("Created kweet from message, posting it in the database");
                kc.PostKweet(kweet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Wowa your message is empty!");
        }
    }

    public void broadcastPostedKweet(KweetDTO kweet, List<User> followers) {
        //send kweet to the author
        sendMessage(getSession(kweet.getAuthorID()), kweet);

        //send kweet to his followers that are logged in
        for (User user : followers) {
            Session session = getSession(user.getId());
            System.out.println("SessionString: " + session);
            if (session != null) {
                sendMessage(getSession(user.getId()), kweet);
            }
        }
    }

    private Session getSession(int userid) {
        return user_Session.get(userid);
    }

    private void sendMessage(Session session, Object message) {
        if(session != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String json = mapper.writeValueAsString(message);
                session.getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
