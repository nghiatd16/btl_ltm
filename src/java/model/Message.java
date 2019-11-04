/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import config.SystemConfig;
import database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nghia
 */
public class Message {
    int id;
    private String content;
    private Timestamp time;
    private int authorId;
    private int roomId;

    public Message() {
    }

    public Message(int id, String content, Timestamp time, int authorId, int roomId) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.authorId = authorId;
        this.roomId = roomId;
    }

    public Message(String content, int authorId, int roomId) {
        this.id = -1;
        this.content = content;
        this.authorId = authorId;
        this.roomId = roomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public static Message getMessageById(int id) throws SQLException{
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT, 
                                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password, 
                                    SystemConfig.MYSQL_TableName);
        String query = "SELECT * FROM `messages` WHERE `id`='"+id+"';";
        ResultSet rs = db.executeQuery(query);
        boolean hasRes = rs.next();
        if(hasRes == false) return null;
        return new Message(id, rs.getString("content"), rs.getTimestamp("time"), 
                            rs.getInt("authorId") , rs.getInt("roomId"));
    }
    
    private static Message createMessage(String content, int authorId, int roomId) throws SQLException{
        try {
            Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT,
                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password,
                    SystemConfig.MYSQL_TableName);
            Connection conn = db.getConnection();
            String query_format ="INSERT INTO `messages` (content, authorId, roomId) VALUES (?, ?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(query_format,
                    Statement.RETURN_GENERATED_KEYS);
            // set parameters for statement
            pstmt.setString(1, content);
            pstmt.setInt(2, authorId);
            pstmt.setInt(2, roomId);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int insertedId = rs.getInt(1);
            return getMessageById(insertedId);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void save() throws SQLException{
        if(this.id == -1){
            Message newMessage = createMessage(this.content, this.authorId, this.roomId);
            this.id = newMessage.id;
        }
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", content=" + content + ", time=" + time + ", authorId=" + authorId + ", roomId=" + roomId + '}';
    }
    
}
