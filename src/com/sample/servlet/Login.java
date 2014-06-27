package com.sample.servlet;

import java.io.IOException;
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*PrintWriter out = response.getWriter();
		out.print("hello");*/
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		System.out.println("****************************");
        String username = request.getParameter("username");
        System.out.println(username);
        String pass = request.getParameter("password");
        System.out.println(pass);
        System.out.println("****************************");

        String sql = "select * from user where username='" + username + "'";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                    "root", "");
            Statement s = conn.prepareStatement(sql);

            java.sql.ResultSet rs = s.executeQuery(sql);
            String un = null;
            String pw = null;
            String name = null;

            while (rs.next()) {
            	System.out.println("------------------------------------");
                un = rs.getString("username");
                System.out.println(un);
                pw = rs.getString("password");
                System.out.println(pw);
                name = rs.getString("name");
                System.out.println(name);
                System.out.println("--------------------------------------");
            }

            PrintWriter pww = response.getWriter();

            if (un.equals(username) && pw.equals(pass)) {
                            // use this or create request dispatcher 
               /* response.setContentType("text/html");
                pww.write("<h1>Welcome, " + name + "</h1>");*/
            	HttpSession session = request.getSession();
                session.setAttribute("user", un);
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30*60);
                Cookie userName = new Cookie("user", un);
                userName.setMaxAge(30*60);
                response.addCookie(userName);
                response.sendRedirect("user_profile.jsp");
            } else {
            	RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                pww.println("<font color=red>Either user name or password is wrong.</font>");
                rd.include(request, response);
               // pww.println("<font color=red>Either user name or password is wrong.</font>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
