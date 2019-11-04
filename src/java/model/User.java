/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import config.SystemConfig;
import database.Database;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nghia
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String fullName;

    private static String hashSHA(String input) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown"
                    + " for incorrect algorithm: " + e);
            return null;
        }
    }

    public User() {
    }

    public User(int id, String username, String password, String fullName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public User(String username, String password, String fullName) {
        this.id = -1;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = hashSHA(password);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static User authenticate(String username, String password) throws SQLException {
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT,
                SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password,
                SystemConfig.MYSQL_TableName);
        String query = "SELECT * FROM `users` WHERE `username`='" + username + "';";
        ResultSet rs = db.executeQuery(query);
        boolean hasRes = rs.next();
        if (hasRes == false) {
            return null;
        }
        int id = rs.getInt("id");
        String _username = rs.getString("username");
        String _password = rs.getString("password");
        String _userFullName = rs.getString("fullName");
        String inputPasswordHashed = hashSHA(password);
        if (inputPasswordHashed.equals(_password)) {
            return new User(id, _username, _password, _userFullName);
        }
        return null;
    }

    public static User getUserById(int id) throws SQLException {
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT,
                SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password,
                SystemConfig.MYSQL_TableName);
        String query = "SELECT * FROM `users` WHERE `id`='" + id + "';";
        ResultSet rs = db.executeQuery(query);
        boolean hasRes = rs.next();
        if (hasRes == false) {
            return null;
        }
        return new User(id, rs.getString("username"), rs.getString("password"), rs.getString("fullName"));
    }

    public static User getUserByUsername(String username) throws SQLException {
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT,
                SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password,
                SystemConfig.MYSQL_TableName);
        String query = "SELECT * FROM `users` WHERE `username`='" + username + "';";
        ResultSet rs = db.executeQuery(query);
        boolean hasRes = rs.next();
        if (hasRes == false) {
            return null;
        }
        return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullName"));
    }

    public static User createUser(String username, String password, String fullName) throws SQLException {
        try {
            Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT,
                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password,
                    SystemConfig.MYSQL_TableName);
            Connection conn = db.getConnection();
            String query_format = "INSERT INTO `users` (username, password, fullName) VALUES (?, ?, ?);";
            password = hashSHA(password);
            PreparedStatement pstmt = conn.prepareStatement(query_format,
                    Statement.RETURN_GENERATED_KEYS);
            // set parameters for statement
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int insertedId = rs.getInt(1);
            return new User(insertedId, username, hashSHA(password), fullName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void updateUser(int id, String username, String password, String fullName) throws SQLException {
        Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT,
                SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password,
                SystemConfig.MYSQL_TableName);
        String query = String.format("UPDATE `users` SET username='%s', password='%s', fullName='%s'"
                + "WHERE id='%d';", username, hashSHA(password), fullName, id);
//        System.out.println(query);
        db.executeQuery(query);
    }

    public static User[] getAllUser() {
        Vector<User> temp = new Vector<>();
        try {

            Database db = new Database(SystemConfig.MYSQL_HOST, SystemConfig.MYSQL_PORT,
                    SystemConfig.MYSQL_Username, SystemConfig.MYSQL_Password,
                    SystemConfig.MYSQL_TableName);
            String query = String.format("SELECT * FROM `users`;");
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                temp.add(new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullname")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        User[] userList = new User[temp.size()];
        return temp.toArray(userList);
    }

    public void save() throws SQLException {
        if (this.id == -1) {
            User newUser = createUser(this.username, this.password, this.fullName);
            this.id = newUser.id;
            this.username = newUser.username;
            this.password = newUser.password;
            this.fullName = newUser.fullName;
        } else {
            updateUser(this.id, this.username, this.password, this.fullName);
        }
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", fullName=" + fullName + '}';
    }

    public static void main(String[] args) throws SQLException {
        User user = User.authenticate("nghiatd", "Nghia.,123");
        System.out.println(user);
    }
}
