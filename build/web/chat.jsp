<%-- 
    Document   : index
    Created on : Oct 18, 2019, 11:38:58 PM
    Author     : nghia
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Room"%>
<%@page import="model.User"%>
<%
    HttpSession userSession = request.getSession();
    int userId = (Integer) userSession.getAttribute("userId");
    User user = User.getUserById(userId);
    String activeName = "";
    ArrayList<Room> joinedRoom = Room.getListJoinedRoom(userId);
    if(joinedRoom.size() >= 1){
        activeName = joinedRoom.get(0).getName();
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Chat Lập Trình Mạng</title>
<link href="<%=request.getContextPath()%>/CSS/font-awesome-4.3.0.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/JS/jquery-3.2.1.js"></script>
<link rel="StyleSheet" id="bootstrap-css" href="<%=request.getContextPath()%>/CSS/bootstrap-4.3.1.css" type="text/css">
<link rel="StyleSheet" href="<%=request.getContextPath()%>/CSS/chat.css" type="text/css">
<script src="<%=request.getContextPath()%>/JS/bootstrap-4.3.1.js"></script>
<!--<script src="<%=request.getContextPath()%>/JS/chat.js"></script>-->
<style>
    nav ul.chat{height:75vh; width:100%;}
    nav ul.chat{overflow:hidden; overflow-y:scroll;}
    nav ul.friend-list{height:75vh; width:100%;}
    nav ul.friend-list{overflow:hidden; overflow-y:scroll;}
  
    html { height:100%; }
    body { position:absolute; top:0; bottom:0; right:0; left:0; }
</style>
</head>
<body>
    <!--Nav bar-->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#"><%=request.getSession().getAttribute("userFullName") %></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item active">
          <a class="nav-link" href="LogOutServlet">Đăng Xuất <span class="sr-only">(current)</span></a>
        </li>
        
      </ul>
    </div>
  </nav>
    <!--End Nav-->
    <div class="container bootstrap snippet" style="max-width: 95%">
        <div class="row">
		<div class="col-md-4 bg-white">
            <div class=" row border-bottom padding-sm" style="height: 40px;">
            	Member
            </div>
            
            <!-- =============================================================== -->
            <!-- member list -->
            <nav>
                
                <ul id="friend-list" class="friend-list" style="width: 115%">
                    <%
                    for(int i=0; i < joinedRoom.size(); i++){
                        Room r = joinedRoom.get(i);
                        String active = "";
                        String fid = "#p" + r.getId();
                        if(i==0){
                            active = "active";
                        }
                    %>
                    <li class="<%=active%> bounceInDown">
                        <a href="<%=fid%>" class="clearfix friendClick">
                            <img src="<%=request.getContextPath()%>/IMG/avatar.jpg" alt="" class="img-circle">
                            <div class="friend-name">	
                                <strong><%=r.getName()%></strong>
                            </div>
                            <div class="last-message text-muted">Hello, Are you there?</div>
                            <small class="time" style="color: #00e600">Online</small>
                        </a>
                    </li>
                    <%
                        }
                    %>
                    
                </ul>
            </nav>
	</div>
        
        <!--=========================================================-->
        <!-- selected chat -->
    	<div class="col-md-8 bg-white ">
            <div class="chat-message">
                <div class="container">
                    <span class="room-img pull-left">
                        <img src="<%=request.getContextPath()%>/IMG/avatar.jpg" alt="User Avatar">
                    </span>
                    <h4 id="room-active-name" style="padding-top: 15px"><%=activeName%></h4>
                </div>
      
                <nav>
                    <ul id="chat-box" class="chat">
                        <li class="left clearfix">
                            <span class="chat-img pull-left">
                                    <img src="<%=request.getContextPath()%>/IMG/avatar.jpg" alt="User Avatar">
                            </span>
                            <div class="chat-body clearfix">
                                <div class="header">
                                        <strong class="primary-font">John Doe</strong>
                                        <small class="pull-right text-muted"><i class="fa fa-clock-o"></i> 12 mins ago</small>
                                </div>
                                <p>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                </p>
                            </div>
                        </li>
                        <li class="right clearfix">
                            <span class="chat-img pull-right">
                                <img src="<%=request.getContextPath()%>/IMG/avatar.jpg" alt="User Avatar">
                            </span>
                            <div class="chat-body clearfix">
                                <div class="header">
                                    <strong class="primary-font">Sarah</strong>
                                    <small class="pull-right text-muted"><i class="fa fa-clock-o"></i> 13 mins ago</small>
                                </div>
                                <p>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula sodales at. 
                                </p>
                            </div>
                        </li>
                        <li class="left clearfix">
                            <span class="chat-img pull-left">
                                <img src="<%=request.getContextPath()%>/IMG/avatar.jpg" alt="User Avatar">
                            </span>
                            <div class="chat-body clearfix">
                                <div class="header">
                                    <strong class="primary-font">John Doe</strong>
                                    <small class="pull-right text-muted"><i class="fa fa-clock-o"></i> 12 mins ago</small>
                                </div>
                                <p>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                </p>
                            </div>
                        </li>
                        <li class="right clearfix">
                            <span class="chat-img pull-right">
                                <img src="<%=request.getContextPath()%>/IMG/avatar.jpg" alt="User Avatar">
                            </span>
                            <div class="chat-body clearfix">
                                <div class="header">
                                    <strong class="primary-font">Sarah</strong>
                                    <small class="pull-right text-muted"><i class="fa fa-clock-o"></i> 13 mins ago</small>
                                </div>
                                <p>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula sodales at. 
                                </p>
                            </div>
                        </li>                    
                        <li class="left clearfix">
                            <span class="chat-img pull-left">
                                <img src="<%=request.getContextPath()%>/IMG/avatar.jpg" alt="User Avatar">
                            </span>
                            <div class="chat-body clearfix">
                                <div class="header">
                                    <strong class="primary-font">John Doe</strong>
                                    <small class="pull-right text-muted"><i class="fa fa-clock-o"></i> 12 mins ago</small>
                                </div>
                                <p>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                </p>
                            </div>
                        </li>
                        
                        

                    </ul>
                </nav>
            </div>
            <div class="chat-box bg-white">
                <form class="input-group" action="">
                    <input id="m" class="form-control border no-shadow no-rounded" placeholder="Type your message here">
                    <span class="input-group-btn">
                        <button class="btn btn-success no-rounded" type="submit">Send</button>
                    </span>
            	</form><!-- /input-group -->	
            </div>            
            </div>        
	</div>
    </div>
</body>
<script>
    let activeRoom = {roomId: <%=joinedRoom.get(0).getId()%>, roomName: '<%=joinedRoom.get(0).getName()%>'};
    const user = {userId: <%=user.getId()%>, username: '<%=user.getUsername()%>', fullName:'<%=user.getFullName()%>'};
    const avatar_url = "<%=request.getContextPath()%>/IMG/avatar.jpg";
//    runSocket(user);
    $('form').submit(function () {
    
        let data = $('#m').val();
        $('#m').val('');
        $('#m').focus();
        let align = "right";
        let time = "13 mins ago";
        let name = "nghiatd"
        template = `
            <li class="`+align+` clearfix">
                <span class="chat-img pull-`+align+`">
                    <img src=` + avatar_url + ` alt="User Avatar">
                </span>
                <div class="chat-body clearfix">
                    <div class="header">
                        <strong class="primary-font">`+name+`</strong>
                        <small class="pull-right text-muted"><i class="fa fa-clock-o"></i>`+time+`</small>
                    </div>
                    <p>`+data+`</p>
                </div>
            </li>
        `
        $("#chat-box").append(template);
        return false;
  });
</script>
<script>
    function setActiveName(){
        let activeName = $(".active")[0].getElementsByClassName("friend-name")[0].innerText;
        $("#room-active-name")[0].innerText = activeName;
    }
    $(".friendClick").click(function () {
        $(".active").removeClass("active");
        let addressValue = $(this).attr("href");
        $(this).parent().addClass("active");
        setActiveName();
        console.log(addressValue.toString().split("#p")[1]);
    });
</script>
</html>
