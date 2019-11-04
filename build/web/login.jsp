<%-- 
    Document   : login
    Created on : Nov 3, 2019, 2:13:10 PM
    Author     : nghia
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
                    <h3>Đăng Nhập</h3>
                    <form action="LogInServlet" method="POST">
                        <% HttpSession userSession = request.getSession();
                            String userFullName = (String) userSession.getAttribute("userFullName");
                            if(userFullName != null && userFullName.length() > 0){
                                response.sendRedirect("chat.jsp");
                            }
                        String loginError = (String) request.getAttribute("loginError");
                            if( loginError != null && loginError.length() > 0 ) {
                                out.println("<p style='color: red; margin: auto'>" + loginError + "</p>");
                            }
                         %>
                        
                        <div class="form-group">
                            <input type='text' name='username' class="form-control" placeholder="Tài Khoản *" value="" required />
                        </div>
                        <div class="form-group">
                            <input type="password" name='password' class="form-control" placeholder="Mật Khẩu *" value="" required/>
                        </div>
                        <div class="form-group" style="margin-left: 35%; margin-bottom: 0; margin-top: 10%">
                            <input type="submit" class="btnSubmit" value="Đăng Nhập" />
                        </div>
                        
                    </form>
                </div>
              
            </div>
        </div>
    </body>
    
</html>
