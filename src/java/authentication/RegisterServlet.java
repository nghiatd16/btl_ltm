/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Room;
import model.User;

/**
 *
 * @author Dell
 */
public class RegisterServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            // đầu tiên lấy toàn bộ user đang có trong csdl về
            User[] userList = User.getAllUser();

            // tạo user mới
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String fullname = req.getParameter("fullname");

            User user = User.createUser(username, password, fullname);
            
            // Cách 1:
            User userTemp = User.getUserByUsername(username);
            // tạo room chat 1-1 giữa user mới và từng user cũ
            for (User u : userList) {
                Room room = Room.createRoom(username + "-" + u.getUsername(), "11");
                Room roomTemp = Room.getRoomByName(username + "-" + u.getUsername());
                roomTemp.insertUserIntoRoom(userTemp.getId());
                roomTemp.insertUserIntoRoom(u.getId());
            }
            
            // Cách 2:
            // lấy ra toàn bộ room
            Room[] roomList = Room.getAllRoom();
            for (Room room : roomList) {
                String roomName = room.getName();
                String[] userOfRoom = roomName.split("-");
                User user1 = User.getUserByUsername(userOfRoom[0]);
                User user2 = User.getUserByUsername(userOfRoom[1]);
                room.insertUserIntoRoom(user1.getId());
                room.insertUserIntoRoom(user2.getId());
            }
            
            Room[] roomList1 = Room.getAllRoom();
            for (Room room : roomList) {
                String roomName = room.getName();
                String[] userOfRoom = roomName.split("-");
                User user1 = User.getUserByUsername(userOfRoom[0]);
                User user2 = User.getUserByUsername(userOfRoom[1]);
                room.insertUserIntoRoom(user1.getId());
                room.insertUserIntoRoom(user2.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
