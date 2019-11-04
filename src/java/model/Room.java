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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nghia
 */
public class Room {
    private int id;
    private String name;
    private String type;

    public Room() {
    }

    public Room(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    
    public Room(String name, String type) {
        this.id = -1;
        this.name = name;
        this.type = type;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public static Room getRoomById(int id) throws SQLException{
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT, 
                                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password, 
                                    SystemConfig.MYSQL_TableName);
        String query = "SELECT * FROM `rooms` WHERE `id`='"+id+"';";
        ResultSet rs = db.executeQuery(query);
        boolean hasRes = rs.next();
        if(hasRes == false) return null;
        return new Room(id, rs.getString("name"), rs.getString("type"));
    }
    
    public static Room getRoomByName(String name) throws SQLException{
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT, 
                                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password, 
                                    SystemConfig.MYSQL_TableName);
        String query = "SELECT * FROM `rooms` WHERE `name`='"+name+"';";
        ResultSet rs = db.executeQuery(query);
        boolean hasRes = rs.next();
        if(hasRes == false) return null;
        return new Room(rs.getInt("id"), name, rs.getString("type"));
    }
    
    public static Room createRoom(String name, String type) throws SQLException{
        try {
            Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT,
                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password,
                    SystemConfig.MYSQL_TableName);
            Connection conn = db.getConnection();
            String query_format ="INSERT INTO `rooms` (name, type) VALUES (?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(query_format,
                    Statement.RETURN_GENERATED_KEYS);
            // set parameters for statement
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int insertedId = rs.getInt(1);
            return new Room(insertedId, name, type);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static void updateRoom(int id, String name, String type) throws SQLException{
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT, 
                                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password, 
                                    SystemConfig.MYSQL_TableName);
        String query = String.format("UPDATE `rooms` SET name='%s', type='%s'"
                + "WHERE id='%d';", name, type, id);
        db.executeQuery(query);
    }
    
    public void save() throws SQLException{
        if(this.id == -1){
            Room newRoom = createRoom(this.name, this.type);
            this.id = newRoom.id;
        }
        else{
            updateRoom(this.id, this.name, this.type);
        }
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name=" + name + ", type=" + type + '}';
    }
    
    public static void insertUserIntoRoom(int userId, int roomId) throws SQLException{
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT, 
                                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password, 
                                    SystemConfig.MYSQL_TableName);
        String query = String.format("INSERT INTO `member_of_rooms` (userId, roomId)"
                + "VALUES (%s, %s);", userId, roomId);
        db.executeQuery(query);
    }
    
    public void insertUserIntoRoom(int userId) throws SQLException{
        insertUserIntoRoom(userId, this.id);
    }
    
    public static ArrayList<Room> getListJoinedRoom(int userId) throws SQLException{
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT, 
                                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password, 
                                    SystemConfig.MYSQL_TableName);
        String query = String.format("SELECT roomId FROM `member_of_rooms` "
                + "WHERE userId=%d;", userId);
        ResultSet rs = db.executeQuery(query);
        ArrayList<Room> final_result = new ArrayList<>();
        while(rs.next()){
            int roomId = rs.getInt("roomId");    
            Room newRoom = getRoomById(roomId);
            if(newRoom.getType().equals("11")){
                query = String.format("SELECT * FROM `member_of_rooms` "
                + "WHERE roomId=%d AND userId!=%d;", roomId, userId);
                ResultSet tmp_rs = db.executeQuery(query);
                tmp_rs.next();
                int friendId = tmp_rs.getInt("userId");
                User friendUser = User.getUserById(friendId);
                newRoom.setName(friendUser.getFullName());
            }
            else{
                newRoom.setName("Nhóm: " + newRoom.getName());
            }
            final_result.add(newRoom);
        }
        return final_result;
    }
    
    public static void main(String[] args) {
        try {
            Room room = Room.getRoomById(2);
            System.out.println(room);
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
