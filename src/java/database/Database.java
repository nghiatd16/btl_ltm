/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nghia
 */

public class Database {
    private String mysqlHost;
    private int mysqlPort;
    private String username;
    private String password;
    private String tableName;

    public Database(String mysqlHost, int mysqlPort, String username, String password, String tableName) {
        
        this.mysqlHost = mysqlHost;
        this.mysqlPort = mysqlPort;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
    }

    public String getMysqlHost() {
        return mysqlHost;
    }

    public void setMysqlHost(String mysqlHost) {
        this.mysqlHost = mysqlHost;
    }

    public int getMysqlPort() {
        return mysqlPort;
    }

    public void setMysqlPort(int mysqlPort) {
        this.mysqlPort = mysqlPort;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public Connection getConnection() throws SQLException, ClassNotFoundException{
        String connectionURL = "jdbc:mysql://" + this.mysqlHost + ":"+this.mysqlPort+"/" + this.tableName +
                "?serverTimezone=UTC&&useSSL=false&&allowPublicKeyRetrieval=true";
        Class.forName("com.mysql.jdbc.Driver"); 
        Connection conn = DriverManager.getConnection(connectionURL, this.username, this.password);
        return conn;
    }
    
    public ResultSet executeQuery(String query) throws SQLException{
        try {
            Connection conn = this.getConnection();
            // Tạo đối tượng Statement.
            Statement statement = conn.createStatement();
            
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
