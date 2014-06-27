package com.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddTeam
 */
@WebServlet("/AddTeam")
public class AddTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTeam() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		    PrintWriter out = response.getWriter();
            try{  
        		Class.forName("com.mysql.jdbc.Driver");  
        		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                        "root", ""); 
        		String teamname = request.getParameter("teamname");
        		Random randomGenerator = new Random();
        		int randomInt=0, m=1;
        		while(m==1)
        		{
        		randomInt = randomGenerator.nextInt(1000);
        		String sql = "select * from team where team_id=" + randomInt ;  
        		System.out.println(randomInt);
        		Statement s = conn.prepareStatement(sql);
        		java.sql.ResultSet rs = s.executeQuery(sql);
                if(!(rs.next()))
                {
                	m=0;
                }
        		}
        		String user = null;
        		HttpSession session = request.getSession();
        	      if(session.getAttribute("user") == null){
        	          response.sendRedirect("login.html");
        	      }else user = (String) session.getAttribute("user");
        		PreparedStatement ps=conn.prepareStatement(  
        		"insert into team values(?,?,?,0)");  
        		ps.setInt(1,randomInt);
        		System.out.println("&&&&&&&&&&&&&&&&&&&&&");
        		System.out.println(randomInt);
        		System.out.println(teamname);
        		System.out.println(user);
        		System.out.println("&&&&&&&&&&&&&&&&&&&&&");
        		ps.setString(2,teamname);
        		ps.setString(3,user);
        	    int i=ps.executeUpdate();  
        		if(i>0) 
        		{
        		session.setAttribute("teamname", teamname);
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30*60);
        		String message = "You are successfully registered...";
        		request.setAttribute("message", message); // This will be available as ${message}
                request.getRequestDispatcher("/user_profile.jsp").forward(request, response);
                
        		}
        		}catch (Exception e2) {System.out.println(e2);}           
        		out.close();  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
