<%-- 
    Document   : create_room
    Created on : Nov 5, 2019, 5:36:58 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://code.jquery.com/jquery-1.11.1.js"></script>
        <link rel="StyleSheet" href="<%=request.getContextPath()%>/CSS/bootstrap-4.3.1.css" type="text/css">
        <link rel="StyleSheet" href="<%=request.getContextPath()%>/CSS/login.css" type="text/css">
        <script src="<%=request.getContextPath()%>/JS/bootstrap-4.3.1.js"></script>
        <script src="<%=request.getContextPath()%>/JS/chat.js"></script>
        <title>Log In</title>
    </head>
    <body>
        <div class="container login-container">
            <div class="row">
                <div class="col-md-6 login-form-1" style="margin: auto; max-width: 100%">
                    <h3>Tạo Nhóm</h3>
                    <form action="CreateRoomServlet" method="POST">
                        
                        <div class="form-group">
                            <input type='text' name='membername' class="form-control" placeholder="Tên Thành Viên" value="" required />
                        </div>
                        <div class="form-group" style="margin-left: 35%; margin-bottom: 0; margin-top: 10%">
                            <input type="submit" class="btnSubmit" value="Tạo" />
                        </div>
                        
                    </form>
                </div>
              
            </div>
        </div>
    </body>
    
</html>

