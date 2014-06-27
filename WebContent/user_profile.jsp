<!doctype html>
<%@ page import="java.sql.*" %>

<% Class.forName("com.mysql.jdbc.Driver"); %>
<html class="no-js" lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Fantasy Cricket</title>
    <link rel="stylesheet" href="css/foundation.min.css" />
    <script src="js/vendor/modernizr.js"></script>
  </head>
  <body>
  
    <nav class="top-bar" data-topbar>
  <ul class="title-area">
    <li class="name">
      <h1><a href="#"><img src="img/logo.png" height="50px" width="50px"></a></h1>
    </li>
  <section class="top-bar-section">
    <!-- Right Nav Section -->
    <ul class="right">
      <li><a href="#">
      <%
      String user = null;
      if(session.getAttribute("user") == null){
          response.sendRedirect("login.html");
      }else user = (String) session.getAttribute("user");
      String userName = null;
      String sessionID = null;
      Cookie[] cookies = request.getCookies();
      if(cookies !=null){
      for(Cookie cookie : cookies){
          if(cookie.getName().equals("user")) userName = cookie.getValue();
          if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
      }
      }
      %>
      Welcome <%=userName %>
      </a></li>
      <li class="has-dropdown">
        <a href="#">Options</a>
        <ul class="dropdown">
          <li><a href="user_profile.jsp">My Team</a></li>
		  <li><a href="make_team.jsp">Make Team</a></li>
		  <li><a href="#">My History</a></li>
        </ul>
      </li>
      <li><a href="Logout">Logout</a>
      </li>
    </ul>

    <!-- Left Nav Section -->
    <ul class="left">
      <li><a href="#">Fantasy Cricket</a></li>
    </ul>
  </section>
</nav>
  <div style="padding:20px 100px 20px 100px;">
  <div style="float:right;" width="40%">
      <% 
            Connection connection = DriverManager.getConnection(
            		"jdbc:mysql://localhost:3306/test", "root", "");

            Statement statement = connection.createStatement() ;
            ResultSet resultset = 
                statement.executeQuery("select * from player left join strengths ON player.skill_id = strengths.skill_id where player.team_id is null") ; 
            Statement statement1 = connection.createStatement() ;
            ResultSet resultset1 = 
                statement1.executeQuery("select amount from user where username ='"+userName+"'");
            Statement statement2 = connection.createStatement() ;
            ResultSet resultset2 = 
                statement2.executeQuery("select * from user where username ='"+userName+"'");
            %>
            <h2>Choose Your Players <span style="float:right;">Price: 
            <% while(resultset1.next()) {%> <%= resultset1.getInt(1) %><% } %></span></h2>
             
      <TABLE>
            <TR>
                <TH>Player Id</TH>
                <TH>Player Name</TH>
                <TH>Bat-Spin</TH>
                <TH>Bat-Pace</TH>
                <TH>Bowl-Spin</TH>
                <TH>Bowl-Pace</TH>
                <TH>Price</TH>
                <TH>Select</TH>
            </TR>
            <% while(resultset.next()){ %>
            <TR>
                <TD> <%= resultset.getInt(1) %></TD>
                <TD> <%= resultset.getString(2) %></TD>
                <TD> <%= resultset.getString(7) %></TD>
                <TD> <%= resultset.getString(8) %></TD>
                <TD> <%= resultset.getString(9) %></TD>
                <TD> <%= resultset.getString(10) %></td>
                <TD> <%= resultset.getString(4) %></td>
                <TD><form><input type="button" value="Add" id="<% resultset.getInt(1); %>" name="select"></form></td>
            </TR>
            <% } %>
        </TABLE> 
  </div>
  <div style="float:left;" width="40%">
     <form data-abide action="AddTeam">
   <div class="name-field">
    <label>Team Name <small>required</small>
      <input type="text" name="teamname" required pattern="[a-zA-Z0-9\.\-\_]{6,}" >
    </label>
    <small class="error">Teamname is required and minimum length must be 6.</small>
  </div>
  <button type="submit">Submit</button>
  <span>${message}</span>
</form>
  </div>
  </div>
    <script src="js/vendor/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
      $(document).foundation();
    </script>
  </body>
</html>