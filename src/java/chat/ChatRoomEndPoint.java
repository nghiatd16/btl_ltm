/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.simple.parser.JSONParser;
import model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
 * @author nghia
 */

@ServerEndpoint(value = "/chatRoom")
public class ChatRoomEndPoint {
    static Set<Session> users = Collections.synchronizedSet(new HashSet<>());
    
    @OnOpen
    public void handleOpen(Session session) throws IOException {
        users.add(session);
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("code", 200);
        job.add("message", "Server opened new socket successfull");
        String username = (String) session.getUserProperties().get("username");
        job.add("require", "userInfo");
//        job.add("userInfo", (String) session.getUserProperties().get("username"));
        session.getBasicRemote().sendText(job.build().toString());
    }
    
    public void handleSetUserInfo(String requestMessage, Session userSession) throws ParseException, IOException{
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(requestMessage);
        long userId = (Long) jsonObject.get("userId");
        String username = (String) jsonObject.get("username");
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("username", username);
        job.add("userId", userId);
        userSession.getBasicRemote().sendText(job.build().toString());
    }
    
    @OnMessage
    public void handleMessage(String message, Session userSession) throws IOException, ParseException {
        handleSetUserInfo(message, userSession);
    }

    @OnClose
    public void handleClose(Session userSession) throws IOException {
        
        String username = (String) userSession.getUserProperties().get("username");
        users.remove(userSession);
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("content", username + " vừa rời khỏi phòng chat");
            job.add("username", "SERVER");
            String resp_json = job.build().toString();
            for (Session session : users) {
                session.getBasicRemote().sendText(resp_json);
            }
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }
}
