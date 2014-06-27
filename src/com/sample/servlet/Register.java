package com.sample.servlet;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		          
		String username=request.getParameter("username"); 
		System.out.println(username);
		String name=request.getParameter("name");  
		System.out.println(name);
		String email=request.getParameter("email");  
		System.out.println(email);
		String password=request.getParameter("password");  
		System.out.println(password);
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                "root", ""); 
		String sql = "select * from user where username='" + username + "'";  
		Statement s = conn.prepareStatement(sql);
		java.sql.ResultSet rs = s.executeQuery(sql);
		String sql1 = "select * from user where email='" + email + "'";  
		Statement s1 = conn.prepareStatement(sql1);
		java.sql.ResultSet rs1 = s1.executeQuery(sql1);
        if(rs.next() || rs1.next())
        {
        	out.print("Username or email already registered with us...");
        }
        else
        {
		PreparedStatement ps=conn.prepareStatement(  
		"insert into user values(?,?,?,?,60)");  
		  
		ps.setString(1,username);  
		ps.setString(2,name);  
		ps.setString(3,email);  
		ps.setString(4,password);  
		          
		int i=ps.executeUpdate();  
		if(i>0)  
		out.print("You are successfully registered...");  
		      
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

