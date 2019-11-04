/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function runSocket (userInfo) {
  endpoint = "ws://localhost:8080/LapTrinhMang/chatRoom";
  console.log("endpoint " + endpoint);
  var socket = new WebSocket(endpoint);
  var username = null;
  $('form').submit(function () {
    let data = $('#m').val();
    if(username === null){
        username = data;
        socket.send(data);
        $('#m').attr("placeholder", "Nhập nội dung trò chuyện");
        $('#m').val('');
    }
    else{
        $('#messages').append($('<li style="color:red;font-weight: bold;font-size:15px; text-align: right;" class="me">').text(username + ": " + data));
        socket.send(data);
        $('#m').val('');
    }
    return false;
  });

  socket.onmessage = function (e) {
    console.log(e.data);
    var responseData = JSON.parse(e.data);
    if(responseData.require === "userInfo"){
        socket.send(JSON.stringify(userInfo));
    }
    return false;
  };
};