<%-- 
    Document   : register
    Created on : Nov 4, 2019, 8:57:45 PM
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
        <title>Register</title>
    </head>
    <body>
        <div class="container login-container">
            <div class="row">
                <div class="col-md-6 login-form-1" style="margin: auto; max-width: 100%">
                    <h3>Đăng Ký</h3>
                    <form action="RegisterServlet" method="POST">
                        <% HttpSession userSession = request.getSession();
                            String userFullName = (String) userSession.getAttribute("userFullName");
                            if(userFullName != null && userFullName.length() > 0){
                                response.sendRedirect("login.jsp");
                            }
                        String registerNotigy = (String) request.getAttribute("registerNotigy");
                            if( registerNotigy != null && registerNotigy.length() > 0 ) {
                                out.println("<p style='color: red; margin: auto'>" + registerNotigy + "</p>");
                            }
                         %>
                         <div class="form-group">
                            <input type="text" name='fullname' class="form-control" placeholder="Họ Tên *" value="" required/>
                        </div>
                        <div class="form-group">
                            <input type='text' name='username' class="form-control" placeholder="Tài Khoản *" value="" required />
                        </div>
                        <div class="form-group">
                            <input type="password" name='password' class="form-control" placeholder="Mật Khẩu *" value="" required/>
                        </div>
                         <div class="form-group">
                            <input type="password" name='password' class="form-control" placeholder="Xác Nhận Mật Khẩu *" value="" required/>
                        </div>
                        <div class="form-group" style="margin-left: 35%; margin-bottom: 0; margin-top: 10%">
                            <input type="submit" class="btnSubmit" value="Đăng Ký" />
                        </div>
                        
                    </form>
                </div>
              
            </div>
        </div>
    </body>
</html>
