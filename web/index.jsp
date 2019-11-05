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
            <% HttpSession userSession = request.getSession();
            String userFullName = (String) userSession.getAttribute("userFullName");
            if(userFullName != null && userFullName.length() > 0){
                response.sendRedirect("chat.jsp");
            }
            else{
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            %>
        </div>
    </body>
    
</html>
